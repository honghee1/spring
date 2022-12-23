package com.koreait.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.koreait.dto.BoardCommentDTO;
import com.koreait.dto.BoardDTO;
import com.koreait.dto.FileDTO;

@Mapper
public interface BoardMapper {

	List<BoardDTO> selectBoardList(int pageNo);
	int selectBoardCount();
	BoardDTO selectBoardDTO(int bno);
	List<FileDTO> selectFileList(int bno);
	List<BoardCommentDTO> selectCommentDTO(int bno);
	int addBoardCount(int bno);
	int deleteBoard(int bno);
	int addBoardComment(BoardCommentDTO dto);
	int insertBoardLike(Map<String, Object> map);
	int deleteBoardLike(Map<String, Object> map);
	int insertBoardHate(Map<String, Object> map);
	int deleteBoardHate(Map<String, Object> map);
	int selectBoardNo();
	int insertBoard(BoardDTO dto);
	int insertFileList(FileDTO fileDTO);
	String selectFile(Map<String, Object> map);
	int deleteBoardComment(int cno);
	int insertBoardCommentLike(Map<String, Object> map);
	int deleteBoardCommentLike(Map<String, Object> map);
	int insertBoardCommentHate(Map<String, Object> map);
	int deleteBoardCommentHate(Map<String, Object> map);
	int selectBoardImageNo();
	int insertBoardImage(Map<String, Object> map);
	String selectImageFile(int fno);
	Map<String, Object> selectNextBefore(int bno);

}