<?PHP
$hostname_localhost="localhost";
$database_localhost="Xgdelrio004_tareas";
$username_localhost="Xgdelrio004";
$password_localhost="*Q48K5Cupz";

$json=array();
	$propietario=$_GET["propietario"];
	$conexion = mysqli_connect($hostname_localhost,$username_localhost,$password_localhost,$database_localhost);
	$consulta="select * from objetosperdidos";
	$resultado=mysqli_query($conexion,$consulta);
		
		
	if ($registro=mysqli_fetch_array($resultado)){	
		$json['desc'][]=$registro[1];
		$json['email'][]=$registro[2];
		$json['url'][]=$registro[3];
		$json['titulo'][]=$registro[4];
		while($registro=mysqli_fetch_array($resultado)){
			$json['desc'][]=$registro[1];
			$json['email'][]=$registro[2];
			$json['url'][]=$registro[3];
			$json['titulo'][]=$registro[4];
		}
		echo json_encode($json);
	} else{
		echo "no hay tareas";
	}
	mysqli_close($conexion);
	



?>