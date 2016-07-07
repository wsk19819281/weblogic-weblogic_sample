<%@ page language="java" %>
<HTML>
<HEAD><TITLE>Display file upload form to the user</TITLE></HEAD>  
<% //  for uploading the file we used Encrypt type of multipart/form-data and input of file type to browse and submit the file %>

  <BODY> 
  <form name="dirForm">
  	<table border="2" >
                    <tr><td colspan="2">
                    <B>PROGRAM FOR UPLOADING THE FILE</B></td>
                    </tr>
      <tr>
                    	<td><b>Choose the destination directory:</b></td>
                    	<td>
                    		<select name="targetDirectory">
								  <option value="c:/tmp/">c:/tmp</option>
								  <option value="c:/temp/">c:/temp</option>
							</select> 
						</td>
                    </tr>
    </table>
                    
  </form>
  <FORM ENCTYPE="multipart/form-data" ACTION="single_upload_page.jsp" METHOD=POST name="submitForm">
         <br><br><br>
          <table border="2" >
                    <tr>
                    	<td><b>Choose the file To Upload:</b></td>
                    	<td><INPUT NAME="F1" TYPE="file"></td>
                    </tr>
                
                    <tr>
                    	<td colspan="2"><p align="right"><INPUT TYPE="submit" VALUE="Send File" onclick="document.submitForm.action='single_upload_page.jsp?targetDirectory='+document.dirForm.targetDirectory.value  "></p></td>
					</tr>
                    
             </table>
  </FORM>
</BODY>
</HTML>

