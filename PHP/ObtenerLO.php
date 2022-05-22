<?PHP
$hostname_localhost="localhost";
$database_localhost="Xjbarbero004_shareit";
$username_localhost="Xjbarbero004";
$password_localhost="*0My0ui41hu";

$json=array();
	$conexion = mysqli_connect($hostname_localhost,$username_localhost,$password_localhost,$database_localhost);
	$consulta="select * from objetosperdidos";
	$resultado=mysqli_query($conexion,$consulta);
		
	if ($registro=mysqli_fetch_array($resultado)){	
		$json['desc'][]=$registro[1];
		$json['email'][]=$registro[2];
		$json['url'][]=$registro[3];
		$json['titulo'][]=$registro[4];
		$json['estado'][]=$registro[4];
		while($registro=mysqli_fetch_array($resultado)){
			$json['desc'][]=$registro[1];
			$json['email'][]=$registro[2];
			$json['url'][]=$registro[3];
			$json['titulo'][]=$registro[4];
			$json['estado'][]=$registro[4];
		}
		echo json_encode($json);
	} else{
		echo "No hay objetos perdidos";
	}
	mysqli_close($conexion);
	
?>
