<?php
include 'connection.php';
$email=$_REQUEST['email'];
$q="select apunteid, titulo, descripcion, url, degree, asignatura from apuntes where email='$email'";
$res=mysqli_query($con,$q);
$array=array();
$i=0;
while($row=mysqli_fetch_assoc($res))
{
$array[$i]=array();
    $array['apunteid']=$row['apunteid'];
    $array['titulo']=$row['titulo'];
    $array['descripcion']=$row['descripcion'];
    $array['url']=$row['url'];
    $array['degree']=$row['degree'];
    $array['asignatura']=$row['asignatura'];
 $i=$i+1;
}
echo json_encode($array);
?>
