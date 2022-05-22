<?php
$DB_SERVER="127.0.0.1"; #la dirección del servidor
$DB_USER="Xjbarbero004"; #el usuario para esa base de datos
$DB_PASS="*0My0ui41hu"; #la clave para ese usuario
$DB_DATABASE="Xjbarbero004_shareit"; #la base de datos a la que hay que conectarse

$params = json_decode( file_get_contents( 'php://input' ), true );
$email = $params['email'];
$password = $params['password'];

#Se establece la conexión:
$con = mysqli_connect($DB_SERVER, $DB_USER, $DB_PASS, $DB_DATABASE);
#Comprobamos conexión
if (mysqli_connect_errno($con)) {
	echo 'Error de conexion: ' . mysqli_connect_error();
	exit();
}
# Ejecutar la sentencia SQL
$resultado = mysqli_query($con, "SELECT * FROM users WHERE email='$email' AND password='$password'");
# Comprobar si se ha ejecutado correctamente
if (!$resultado) {
	echo 'Ha ocurrido algún error: ' . mysqli_error($con);
}
#Acceder al resultado
$datos = array();
while($row = $resultado->fetch_assoc()){
	$datos[] = array('name'=>$row['name'],
					'degree'=>$row['degree']);
}
echo json_encode($datos);
?>	