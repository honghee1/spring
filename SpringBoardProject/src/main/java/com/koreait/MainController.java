package com.koreait;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import org.apache.ibatis.annotations.Param;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.koreait.dto.BoardCommentDTO;
import com.koreait.dto.BoardDTO;
import com.koreait.dto.FileDTO;
import com.koreait.dto.MemberDTO;
import com.koreait.dto.QnADTO;
import com.koreait.service.BoardService;
import com.koreait.service.MemberService;
import com.koreait.service.QnAService;
import com.koreait.vo.PaggingVO;

@Controller
public class MainController {
	private BoardService boardService;
	private MemberService memberService;
	private QnAService qnaService;
	

	public MainController(BoardService boardService, MemberService memberService, QnAService qnaService) {
		super();
		this.boardService = boardService;
		this.memberService = memberService;
		this.qnaService = qnaService;
	}

	@RequestMapping("/")
	public String main(@RequestParam(name = "pageNo", defaultValue = "1") int pageNo, Model model) {
//		System.out.println(pageNo);
		List<BoardDTO> list = boardService.selectBoardList(pageNo);
		model.addAttribute("list", list);

		// 페이징 처리
		int count = boardService.selectBoardCount();
		PaggingVO vo = new PaggingVO(count, pageNo, 10, 5);
		model.addAttribute("pagging", vo);

		return "main";
	}
	
	@RequestMapping("/main.do")
	public String main() {
		return "redirect:/";
	}

	@RequestMapping("/boardView.do")
	public String boardView(int bno, Model model, HttpSession session) {
		BoardDTO dto = boardService.selectBoardDTO(bno);
		List<FileDTO> flist = boardService.selectFileList(bno);
		List<BoardCommentDTO> comment = boardService.selectCommentDTO(bno);
		Map<String, Object> map = boardService.selectNextBefore(bno);
		System.out.println(map);
		// 게시글 조회수 증가
		HashSet<Integer> set = (HashSet<Integer>) session.getAttribute("bno_history");
		if (set == null)
			set = new HashSet<Integer>();

		if (set.add(bno))
			boardService.addBoardCount(bno);
		session.setAttribute("bno_history", set);
		model.addAttribute("board", dto);
		model.addAttribute("flist", flist);
		model.addAttribute("comment", comment);
		model.addAttribute("other", map);
		return "board_detail_view";
	}

	@RequestMapping("loginView.do")
	public String loginView() {
		return "login";
	}

	@RequestMapping("login.do")
	public String login(String id, String pass, HttpSession session) {
		MemberDTO dto = memberService.login(id, pass);
		if (dto != null) {
			session.setAttribute("login", true);
			session.setAttribute("id", dto.getId());
			session.setAttribute("name", dto.getName());
			session.setAttribute("grade", dto.getGradeNo());
			return "redirect:/";
		} else {
			session.setAttribute("login", false);
			return "login";
		}
	}
	
	@RequestMapping("/logout.do")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
	
	@RequestMapping("/deleteBoard.do")
	public String boardDelete(int bno) {
		// 파일 삭제
		// 파일 삭제 목록을 불러옴
		List<FileDTO> list = boardService.selectFileList(bno);
		for(int i=0;i<list.size();i++) {
			File file = new File(list.get(i).getPath()); // 전체 경로 받는 부분
			try {
				if(file.delete()) {
					System.out.println("파일 삭제 성공");
				}
			} catch(Exception e) {
				System.out.println(e.getMessage());
			}
		}
		// 게시글 삭제
		boardService.deleteBoard(bno);
		return "redirect:/";
	}
	@RequestMapping("/insertComment.do")
	public void insertComment(int bno, String writer, String content, HttpServletResponse response) {
		BoardCommentDTO dto = new BoardCommentDTO(bno, content, writer);
		int result = boardService.insertBoardComment(dto);
		try {
			response.getWriter().write(String.valueOf(result));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/plusLikeHate.do")
	public void plusLikeHate(int bno, int mode, 
			HttpSession session, HttpServletResponse response) {
		int result = 0;
		String id = (String) session.getAttribute("id");
		if(mode == 0) {
			//좋아요
			result = boardService.insertBoardLike(bno,id);
		}else {
			//싫어요
			result = boardService.insertBoardHate(bno, id);
		}
		try {
			response.getWriter().write(String.valueOf(result));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/boardWriteView.do")
	public String boardWriteView() {
		return "board_write_view";
	}
	
	@RequestMapping("/boardWrite.do")
	public String boardWrite(BoardDTO dto, MultipartHttpServletRequest request) {
		// 게시물 작성
		int bno = boardService.inserBoard(dto);
		// 파일 업로드
		// 저장할 경로
		String root = "c:\\flieUpload\\";
		File userRoot = new File(root);
		if(!userRoot.exists()) {
			userRoot.mkdir();
		}
		
		List<MultipartFile> fileList = request.getFiles("file");
		int i = 1;
		for(MultipartFile f : fileList) {
			String originalFileName = f.getOriginalFilename();
			if(f.getSize() == 0) continue;
			File uploadFile = new File(root + "\\" + originalFileName);
			boardService.insertFileList(new FileDTO(uploadFile, bno, i));
			i++;
			try {
				f.transferTo(uploadFile); // 실제로 전송
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			} 
		}
		return "redirect:/boardView.do?bno=" + bno;
	}
	
	@RequestMapping("/fileDown.do")
	public void fileDown(int bno, int fno, HttpServletResponse response) throws IOException {
		String path = boardService.selectFile(bno, fno);
		File file = new File(path);
		response.setHeader("Content-Disposition", "attachement;fileName="+file.getName());
		response.setHeader("Content-Transfer-Encoding", "binary");
		response.setContentLength((int) file.length());
		
		FileInputStream fis = new FileInputStream(file);
		BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
		byte[] buffer = new byte[1024*1024];
		while(true) {
			int size = fis.read(buffer);
			if(size == -1) break;
			bos.write(buffer, 0, size);
			bos.flush();
		}
		bos.close();
		fis.close();
	}
	
	@RequestMapping("/registerView.do")
	public String registerView() {
		return "register";
	}
	
	@RequestMapping("/register.do")
	public String register(MemberDTO dto) {
		memberService.insertMember(dto);
		return "redirect:/";
	}
	
	@RequestMapping("/memberManageView.do")
	public String memberManageView(Model model) {
		List<MemberDTO> list = memberService.selectAllMember();
		model.addAttribute("list", list);
		return "member_manager";
	}
	
	@RequestMapping("/memberDelete.do")
	public void memberDelete(String id,HttpServletResponse response) throws IOException {
		System.out.println(id);
		int result = memberService.deleteMember(id);
		response.getWriter().write(String.valueOf(result));
	}
	
	@RequestMapping("memberUpdate.do")
	public void memberUpdate(MemberDTO dto, HttpServletResponse response) throws IOException {
		System.out.println(dto);
		int result = memberService.updateMember(dto);
		response.getWriter().write(String.valueOf(result));
	}
	
	@RequestMapping("/memberSearch.do")
	public ResponseEntity<List<MemberDTO>> memberSearch(String kind, String search) {
		List<MemberDTO> list = memberService.selectMember(kind, search);
		return ResponseEntity.ok(list); // json으로 보내는 방법
		
	}
	
	@RequestMapping("/deleteComment.do")
	public String deleteComment(int bno, int cno) {
		boardService.deleteBoardComment(cno);
		return "redirect:/boardView.do?bno="+bno;
	}
	
	@RequestMapping("/commentLike.do")
	public String commentLike(int cno, int bno, HttpSession session) {
		String writer = (String) session.getAttribute("id");
		boardService.insertBoardCommentLike(cno,writer);
		return "redirect:/boardView.do?bno="+bno;
	}
	
	@RequestMapping("/commentHate.do")
	public String commentHate(int cno, int bno, HttpSession session) {
		String writer = (String) session.getAttribute("id");
		boardService.insertBoardCommentHate(cno,writer);
		return "redirect:/boardView.do?bno="+bno;
	}
	
	@RequestMapping("/fileUpload.do")
	public void fileUpload(@RequestParam(value = "upload")MultipartFile fileload,
			HttpServletResponse response, HttpSession session) {
		//서버에 파일을 저장할 때에는 파일명을 시간값으로 변경
	    //DB에 저장할 때에는 원본 파일명과 시간값을 모두 저장
	    //filename 취득
		String originFileName = fileload.getOriginalFilename();
		//upload 경로 설정
	    String root = "c:\\fileupload\\";

	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy_mm_dd_hh_MM_ss");
	    String date = sdf.format(Calendar.getInstance().getTime());
	    System.out.println("원본파일 : " + originFileName);
	    System.out.println(originFileName.indexOf("."));
	    System.out.println(originFileName.substring(originFileName.indexOf(".")+1));

	    String fileName = date+"_"+session.getAttribute("id") 
	    			+originFileName.substring(originFileName.indexOf(".")) ;
	    File file = new File(root + "\\" + fileName);
	    int fno = boardService.uploadImage(file.getAbsolutePath());
	    try {
	    	PrintWriter pw = response.getWriter();
	    	//실제 파일이 업로드 되는 부분
	    	fileload.transferTo(file);
	    	JSONObject obj = new JSONObject();
	    	obj.put("uploaded", true);
	    	obj.put("url", "imageDown.do?fno="+fno);
	    	pw.write(obj.toString());
	    } catch (IOException e) {
	    	JSONObject obj = new JSONObject();
	    	obj.put("uploaded", false);
	    	JSONObject msg = new JSONObject();
	    	msg.put("message", "파일 업로드 중 에러 발생");
	    	obj.put("error", msg);
	    }
	}
	@RequestMapping("/imageDown.do")
	public void imageLoad(int fno,HttpServletResponse response) throws Exception {
		String path = boardService.selectImageFile(fno);
		File file = new File(path);
		response.setHeader("Content-Disposition", "attachement;fileName="+file.getName());
		response.setHeader("Content-Transfer-Encoding", "binary");
		response.setContentLength((int)file.length());

		FileInputStream fis = new FileInputStream(file);
		BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
		byte[] buffer = new byte[1024*1024];
		while(true) {
			int size = fis.read(buffer);
			if(size == -1) break;
			bos.write(buffer,0,size);
			bos.flush();
		}
		bos.close();
		fis.close();
	}
	
	@RequestMapping("/qnaView.do")
	public String qnaView(HttpSession session, Model model) {
		int page = 1;
		String writer = (String) session.getAttribute("id");
		if(writer == null)
			return "redirect:/loginView.do";

		List<QnADTO> list = qnaService.selectQnaList(writer,page);
		model.addAttribute("list", list);
		return "qna";
	}
	
	@RequestMapping("/sendQnA.do")
	public String sendQnA(QnADTO dto, HttpSession session) {
		dto.setWriter((String) session.getAttribute("id"));
		qnaService.insertQnA(dto);
		return "redirect:/qnaView.do";
	}
	
	@RequestMapping("/nextQnaList.do")
	public ResponseEntity<HashMap<String, Object>> nextQnaList(int page, HttpSession session) {
		// 1. 아이디값 읽어옴
		String writer = (String) session.getAttribute("id");
		// 2. 해당 페이지 목록을 읽어옴 List<QnADTO>
		List<QnADTO> list = qnaService.selectQnaList(writer, page);
		// 3. 다음 페이지 목록 읽어옴
		int nextPage = 0;
		if(!qnaService.selectQnaList(writer, nextPage+1).isEmpty())
			nextPage = page+1;
		// 4. HashMap에 리스트와 다음페이지 번호를 저장
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("nextPage", nextPage);
		map.put("list", list);
		// 5. map에 있는 내용을 리턴
		return ResponseEntity.ok(map);
	}
	
	@RequestMapping("/qnaAdminView.do")
	public String qnaAdminView(@RequestParam(name = "pageNo", defaultValue = "1") int page, Model model) {
		List<QnADTO> list = qnaService.selectQnaAdminList(page);
		int count = qnaService.selectCount();
		PaggingVO vo = new PaggingVO(count, page, 5, 5);
		model.addAttribute("list", list);
		model.addAttribute("page", vo);
		return "admin_qna";
	}
	
	@RequestMapping("/adminQnaDetailView.do")
	public String adminQnaDetailView(int qno, Model model) {
		QnADTO dto = qnaService.selectQna(qno);
		dto.setContent(dto.getContent().replaceAll("\n", "<br>"));
		dto.setResponse(dto.getResponse().replaceAll("\n", "<br>"));
		if(dto.getStatus() == 0)
			qnaService.updateStatusView(qno);
		model.addAttribute("dto", dto);
		return "admin_qna_view";
	}
	
	@RequestMapping("/answer.do")
	public String answer(int qno, String response, HttpSession session) {
		String id = (String) session.getAttribute("id");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		
		response = "답변자 : " + id + "작성일 : " + sdf.format(Calendar.getInstance().getTime()) + "\n" + response;
		qnaService.updateResponse(qno, response);
		qnaService.updateStatus(qno);
		return "redirect:/adminQnaDetailView.do?qno=" + qno;
	}
	
	
}














