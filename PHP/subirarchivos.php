<?php
 
ServerConfig();

$PdfUploadFolder = 'apuntes/';
 
$ServerURL = 'http://ec2-52-56-170-196.eu-west-2.compute.amazonaws.com/jbarbero004/WEB/shareit/'.$PdfUploadFolder;
 
if($_SERVER['REQUEST_METHOD']=='POST'){
 
 if(isset($_POST['titulo']) and isset($_FILES['pdf']['name'])){

 $con = mysqli_connect(HostName,HostUser,HostPass,DatabaseName);
 
 $PdfName = $_POST['titulo'];

 $PdfEmail = $_POST['email'];

 $PdfDescr = $_POST['descripcion'];

 $PdfDegree = $_POST['degree'];

 $PdfAsignatura = $_POST['asignatura'];
 
 $PdfInfo = pathinfo($_FILES['pdf']['name']);
 
 $PdfFileExtension = $PdfInfo['extension'];
 
 $PdfFileURL = $ServerURL . $PdfName . '.' . $PdfFileExtension;
 
 $PdfFileFinalPath = $PdfUploadFolder . $PdfName . '.'. $PdfFileExtension;
 
 try{
 
 move_uploaded_file($_FILES['pdf']['tmp_name'],$PdfFileFinalPath);
 
 $InsertTableSQLQuery = "INSERT INTO apuntes ('apunteid', 'email', 'titulo', 'descripcion', 'url', 'degree', 'asignatura') VALUES (NULL, '$PdfEmail', '$PdfName',  '$PdfDescr', '$PdfFileURL',  '$PdfDegree',  '$PdfAsignatura') ;";

 mysqli_query($con,$InsertTableSQLQuery);

 }catch(Exception $e){} 
 mysqli_close($con);
 
 }
}

function ServerConfig(){
 
define('HostName','127.0.0.1');
define('HostUser','Xjbarbero004');
define('HostPass','*0My0ui41hu');
define('DatabaseName','Xjbarbero004_shareit');
 
}

?>
