<?php

//connecting to the db
$con = mysqli_connect("127.0.0.1","Xjbarbero004","*0My0ui41hu","Xjbarbero004_shareit") or die("Unable to connect");
 
//sql query
$sql = "SELECT * FROM `apuntes`";
 
//getting result on execution the sql query
$result = mysqli_query($con,$sql);
 
//response array
$response = array();
 
$response['error'] = false;
 
$response['message'] = "Apuntes aparecen correctamente";
 
$response['apuntes'] = array();
 
//traversing through all the rows
 
while($row =mysqli_fetch_array($result)){
    $temp = array();
    $temp['email'] = $row['email'];
    $temp['titulo'] = $row['titulo'];
    $temp['descripcion'] = $row['descripcion'];
    $temp['url'] = $row['url'];
    $temp['degree'] = $row['degree'];
    $temp['asignatura'] = $row['asignatura'];
    array_push($response['apuntes'],$temp);
}
 
echo json_encode($response);

?>
