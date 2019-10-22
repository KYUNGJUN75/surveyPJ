package com.pjt.survey;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
public class WordSearchJsp {

    static ArrayList arr = new ArrayList();

    /**
    * 검색한 파일을 담을 클래스
    */
    class SearchFile {
        String path; // 파일명 혹은 디렉토리명
        String dirPath; // 파일명 혹은 디렉토리명
        boolean dir = false; // 디렉토리 여부
        List<Integer> lines; // 검색어를 발견한 라인 번호 목록
        List<SearchFile> childs; // 디렉토리의 경우 하위 파일 목록
        /**
        * path : 파일명 혹은 디렉토리명
        * isDir : 디렉토리면  true 아니면 false
        */
        SearchFile(String dirPath, String path, boolean isDir) {
           this.path = path;
           this.dirPath = dirPath;
           this.dir = isDir;
        }
        String getPath() {
           return path;
        }
        String getDirPath() {
            return dirPath;
         }
        void setChilds(List<SearchFile> childs) {
           this.childs = childs;
        }
        List<SearchFile> getChilds() {
           return childs;
        }
        boolean isDir() {
           return dir;
        }
        void setLines(List<Integer> lines) {
           this.lines = lines;
        }
        List<Integer> getLines() {
           return lines;
        }
        /**
        * @return 키워드가 포함된 라인이 있다면 true, 없으면 false
        */
        boolean isMatch() {
           return (lines != null && lines.size() > 0);
        }
    }
    /**
    * 확장자 필터
    */
    class ExtFilter implements FileFilter {
        String ext;
        ExtFilter(String ext) {
           this.ext = ext;
        }
        /**
        * @param  f 검사할 파일
        * @return 디렉토리면 true, 파일인 경우 ext 확장자를 가진 경우 true
        */
        public boolean accept(File f) {
           // System.out.println("******************* check pattern [" + dir.getAbsolutePath() + "]     name : " + name + " with ext : " + this.ext);
           // if(dir.isDirectory()) return true;
           if(f.isDirectory()) return true;
           return f.getName().toLowerCase().endsWith("." + this.ext);
        }
    }
    /** 검색어 */
    private String keyword;
    /** 파일을 걸러낼 필터, null 이면 모든 파일 대상 */
    private FileFilter filter = null;
    public WordSearchJsp(String keyword) {
       this.keyword = keyword;
    }
    public void setExtension(String ext) {
       this.filter = new ExtFilter(ext);
    }
    /**
    * 주어진 파일에서 검색어를 찾고 검색어가 존재하는 라인 번호 목록 리턴
    * @param  file 검색 대상 파일
    * @return 검색어가 포함된 라인 번호 목록. 라인이 없어도 리스트는 리턴됨. size() == 0 인 경우 검색어 없음
    */
    protected List<Integer> searchLines(File file) {
        List<Integer> lines = new ArrayList<Integer>();
        try {
            Scanner scanner = new Scanner(file);
            int line = 0;
            while(scanner.hasNextLine()) {
               line++;
               if(scanner.nextLine().indexOf(keyword) > -1) {
                  lines.add(line);
               }
            }
            } catch(FileNotFoundException ex) {
            // System.out.println(ex.getMessage());
        }
        return lines;
    }
    /**
    * 파일 검색 (recursive)
    *
    * @param dir   검색할 디렉토리
    * @param files 검색 결과를 담을 리스트
    */
    protected void internalSearch(File dir, List<SearchFile> files) {
        File[] fileList;
        // 1. 파일 필터가 있는 경우 핕터를 통과한 파일 목록 얻기, 없으면 모든 파일
        if(filter != null) fileList = dir.listFiles(filter);
        else fileList = dir.listFiles();
        if(fileList != null) {
            for(File f : fileList) {
                if(f.isFile()) { // 2. 파일 이면 파일내에 검색어가 존재하는지 검사 후 파일 목록에 추가
                   SearchFile sf = new SearchFile(f.getPath(), f.getName(), false);
                   //sf.setLines(searchLines(f));
                   files.add(sf);
                   } else if (f.isDirectory()) { // 3. 디렉토리면 해당 디렉토리 하위 파일들에 대해 반복
                   SearchFile sf = new SearchFile(f.getPath(), f.getName(), true);
                   List<SearchFile> childs = new ArrayList<SearchFile>();
                   internalSearch(f, childs);
                   sf.setChilds(childs);
                   files.add(sf);
                }
            }
        }
    }
    /**
    * 검색 시작 메소드
    * @param  startDirectory 파일 검색을 시작할 디렉토리
    * @return 파일 목록을 담은 리스트
    */
    public List<SearchFile> search(String startDirectory) {
       List<SearchFile> files = new ArrayList<SearchFile>();
       internalSearch(new File(startDirectory), files);
       return files;
    }
    /**
    * 검색된 파일 목록을 출력 (recursive)
    *
    * @param files 파일 목록
    * @param out   출력 대상. 보통 System.out 파일에 쓰고자 한다면 해당 파일의 PrintStream
    * @param depth 출력 깊이. 그냥 0으로 호출.
    */
    public void printSearchFiles(List<SearchFile> files, PrintStream out, int depth) throws IOException, RowsExceededException, WriteException{

       for(SearchFile sf : files) {
//           for(int i = 0; i < depth; i++) out.print("    ");
           if(sf.isDir()) {
              // 디렉토리의 경우 식별자 추가 해서 출력
//              out.println("sf.getPath() 0 [" + sf.getPath() + "]");
//              System.out.println("sf.getDirPath() 2 >>>>>> ["+sf.getDirPath()+"]");
              // 자식 파일들이 있으면 depth에 1을 추가해 반복
              if(sf.getChilds() != null) printSearchFiles(sf.getChilds(), out, depth+1);

           } else {

//        	   System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>");
//               System.out.println("sf.getPath() 1 >>>>>> ["+sf.getPath()+"]");
               System.out.println("sf.getDirPath() 2 >>>>>> ["+sf.getDirPath()+"]");
        	   
               if("tkxmApplInsert.jsp".equals(sf.getPath())){
            	   String aa = sf.getPath();
            	   System.out.println("aa======["+aa+"]");
               }
               
                File file = new File(sf.getDirPath());
                Scanner scan = new Scanner(file);

                String result = "";
                int iRst = 0;
                String[] str = null;
                int eindex = 0;
                int sindex = 0;

                while(scan.hasNext()){

                    String temp = scan.nextLine().toUpperCase();
                    boolean isFirst = true;

                    iRst++;

                    System.out.println(iRst);

                     while(true){

                         int index = temp.indexOf("INTO ");

                         if(index == -1) break;

                         if(!isFirst){
                             break;
                         }else{

                             isFirst = false;

                             if(temp.contains("INTO ")){

                                 sindex = temp.indexOf("INTO ");
                                 eindex = temp.indexOf("(");

                                 //                                 System.out.println("sindex ["+sindex+"] : eindex ["+eindex+"]");

                                 if(sindex != -1){

                                	 if(eindex != -1 && sindex <= eindex){
                                		 str = new String[]{sf.getDirPath(), file.getName(), temp.substring(sindex, eindex), String.valueOf(iRst)};
                                	 }else{
                                		 str = new String[]{sf.getDirPath(), file.getName(), temp.substring(sindex), String.valueOf(iRst)};
                                	 }
                                	 
                                	 arr.add(str);
                                 }


                             }else{

                                 break;

                             }

                         }

                     }

                     while(true){

                         int index = temp.indexOf("UPDATE ");

                         if(index == -1) break;

                         if(!isFirst){
                             break;
                         }else{

                             isFirst = false;

                             if(temp.contains("UPDATE ")){

                                 sindex = temp.indexOf("UPDATE ");
                                 eindex = temp.indexOf("SET");
                                 
//                                 System.out.println("sindex ["+sindex+"] : eindex ["+eindex+"]");

                                 if(sindex != -1){
                                 
	                                 if(eindex != -1 && sindex <= eindex){
	                            		 str = new String[]{sf.getDirPath(), file.getName(), temp.substring(sindex, eindex), String.valueOf(iRst)};
	                            	 }else{
	                            		 str = new String[]{sf.getDirPath(), file.getName(), temp.substring(sindex), String.valueOf(iRst)};
	                            	 }

	                            	 arr.add(str);
                                 }
                                 


                             }else{

                                 break;

                             }

                         }

                     }

                     while(true){

                         int index = 0;
                         
                         index = temp.indexOf("FROM ");

                         if(index == -1) break;

                         if(!isFirst){
                              break;
                         }else{

                              isFirst = false;

                              if(temp.contains("FROM ")){

                            	  sindex = temp.indexOf("FROM ")+5;
                                  eindex = temp.indexOf("WHERE");
                                  if(eindex == -1){
                                	  eindex = temp.indexOf("ORDER");
                                  }
                                  if(eindex == -1){
                                	  eindex = temp.indexOf("GROUP");
                                  }

                                  if(sindex != -1){

//                                	  if(temp.length() >= sindex+4)
//                                	  if("DUAL".equals(temp.substring(sindex, sindex+4).trim())) continue;
//                                	  if("(".equals(temp.substring(sindex, sindex+1).trim())) continue;
//                                	  if("(".equals(temp.substring(sindex, sindex+2).trim())) continue;

//                                	  if(temp.length() >= sindex+6)
//                                      if("DUAL".equals(temp.substring(sindex, sindex+6).trim())) continue;
//                                	  if(temp.length() >= sindex+7)
//                                	  if("(SELECT".equals(temp.substring(sindex, sindex+7).trim())) continue;
//                                	  if(temp.length() >= sindex+9)
//                                	  if("(SELECT".equals(temp.substring(sindex, sindex+9).trim())) continue;
//                                	  if(temp.length() >= sindex+8)
//                                	  if("( SELECT".equals(temp.substring(sindex, sindex+8).trim())) continue;
//                                	  if(temp.length() >= sindex+9)
//                                	  if("( SELECT".equals(temp.substring(sindex, sindex+9).trim())) continue;
//                                	  if(temp.length() >= sindex+10)
//                                	  if("( SELECT".equals(temp.substring(sindex, sindex+10).trim())) continue;
//                                	  if(temp.length() >= sindex+10)
//                                	  if("(   SELECT".equals(temp.substring(sindex, sindex+10).trim())) continue;
//                                	  if(temp.length() >= sindex+11)
//                                	  if("(  SELECT".equals(temp.substring(sindex, sindex+11).trim())) continue;
//                                	  if(temp.length() >= sindex+11)
//                                	  if("(    SELECT".equals(temp.substring(sindex, sindex+11).trim())) continue;

//                                	  System.out.println("temp >>>>>>>>> ["+temp+"]");
//                                	  System.out.println("sindex >>>>>>>>> ["+sindex+"]");
//                                	  System.out.println("eindex >>>>>>>>> ["+eindex+"]");
                                	  if(eindex != -1 && sindex <= eindex){
                                 		 str = new String[]{sf.getDirPath(), file.getName(), temp.substring(sindex, eindex), String.valueOf(iRst)};
                                 	  }else{
                                 		 str = new String[]{sf.getDirPath(), file.getName(), temp.substring(sindex), String.valueOf(iRst)};
                                 	  }
                         	  
                                 	  arr.add(str);
                                  }

                              }else{

                                   break;
                              }


                         }

                     }
                     
                 }
                
            }
           
        }

    }

    public static void main(String[] args) throws RowsExceededException, WriteException, IOException {

    	try{
    		
    		args = new String[]{"FROM", "C:/DICES_DEV/workspaces/dices-ept-client/src/app/care/trns/", "ts"};
    		
	        if(args.length == 0) {
		        System.out.println("Usage: java WordSearch keyword [시작디렉토리] [확장자]");
		        System.exit(1);
	        }
	        
//	        System.out.println("args[0] ["+args[0]+"]");
//	        System.out.println("args[1] ["+args[1]+"]");
	        
	        WordSearchJsp wordSearch = new WordSearchJsp(args[0]); // 검색어 설정
	        // 시작 디렉토리를 지정 하지 않으면 현재 디렉토리 사용
	        String startDirectory = (new File("C:/DICES_DEV/workspaces/dices-ept-client/src/app/care/trns/").getAbsoluteFile()).getAbsolutePath();
	        if(args.length == 2) startDirectory = args[1];
	        // 확장자가 전달되면 필터링 설정
	        if(args.length == 3) wordSearch.setExtension(args[2]);
	        // 파일 목록을 찾고
	        List<SearchFile> files = wordSearch.search(startDirectory);
	        // System.out 에 파일 목록 출력
	        wordSearch.printSearchFiles(files, System.out, 0);
	
	        String[] str1 = null;
	
	        System.out.println("arr.size ["+arr.size()+"]");
	
	        WritableWorkbook workbook = Workbook.createWorkbook(new File("C:/KINS/workspace/app/src/LoadDataJsp.xls"));
	        WritableSheet s1 = workbook.createSheet("sheet0",0);
	//        WritableSheet s2 = workbook.createSheet("sheet1",1);
	//        WritableSheet s3 = workbook.createSheet("sheet2",2);
	
	        for(int j=0; j < arr.size(); j++){
	
	          str1 = (String[])arr.get(j);
	
//	          System.out.println("fileName ["+str1[0]+"] ["+str1[1]+"]");
	
	          Label label1 = new Label(0,j,str1[0]);
	          Label label2 = new Label(1,j,str1[1]);
	          Label label3 = new Label(2,j,str1[3]);
	          Label label4 = new Label(3,j,str1[2].replaceAll("[+]", "").replaceAll("\\\\T", "").replaceAll("\\\\R", "").replaceAll("\\\\N", "").replaceAll("�", "").trim());
          
	          s1.addCell(label1);
	          s1.addCell(label2);
	          s1.addCell(label3);
	          s1.addCell(label4);
	
	        }
	
	        workbook.write();
	        workbook.close();
    	}catch (Exception e){
    		e.printStackTrace();
    	}
    }
}