<?PHP
$hostname_localhost="localhost";
$database_localhost="Xjbarbero004_shareit";
$username_localhost="Xjbarbero004";
$password_localhost="*0My0ui41hu";

$json=array();
	
		$conexion = mysqli_connect($hostname_localhost,$username_localhost,$password_localhost,$database_localhost);

		$consulta="select * from objetosperdidos";
		$resultado=mysqli_query($conexion,$consulta);
		
		while($registro=mysqli_fetch_array($resultado)){
			$json['objetosperdidos'][]=$registro;
		}
		mysqli_close($conexion);
		echo json_encode($json);

?>