<?php
include 'connection.php';
$array=array();
$q="select * from menu";
$result=mysqli_query($con,$q);
$i=0;
while($row=mysqli_fetch_assoc($result))
{
   $array[$i]=array();
   $array[$i]['descripcion']=$row['descripcion'];
   $i=$i+1;
  
}
echo json_encode($array);
?>
