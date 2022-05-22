<?PHP
$hostname_localhost="localhost";
$database_localhost="Xjbarbero004_shareit";
$username_localhost="Xjbarbero004";
$password_localhost="*0My0ui41hu";

$json=array();
	$descripcion=$_GET["nombre"];
	$email=$_GET["correo"];
	$url=$_GET["descripcion"];
	$titulo=$_GET["ep"];
	$estado=$_GET["imgURL"];

	$conexion = mysqli_connect($hostname_localhost,$username_localhost,$password_localhost,$database_localhost);
	$consulta="insert into objetosperdidos(descripcion, email, url, titulo, estado) values ('$descripcion', '$email', '$url', '$titulo', '$estado');
	$resultado=mysqli_query($conexion,$consulta);
		
		
	if($resultado){
		echo 'insertada';
	}
	else{
		echo 'noInsertada';
	}
	mysqli_close($conexion);
	



?>