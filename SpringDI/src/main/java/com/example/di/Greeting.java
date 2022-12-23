	package com.example.di;
	
	public class Greeting {
		private final long id;
		private String content;
		
		public Greeting(long id, String content) {
			System.out.println("Greeting Constructor");
			this.id = id;
			this.content = content;
		}
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
		public long getId() {
			return id;
		}
		
		@Override
		public String toString() {
			return "Greeting [id=" + id + ", content=" + content + "]";
		}
		
		
	}
