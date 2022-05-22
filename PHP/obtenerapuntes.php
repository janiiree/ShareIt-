<?PHP
$hostname_localhost="localhost";
$database_localhost="Xjbarbero004_shareit";
$username_localhost="Xjbarbero004";
$password_localhost="*0My0ui41hu";

$json=array();
	$conexion = mysqli_connect($hostname_localhost,$username_localhost,$password_localhost,$database_localhost);
	$consulta="select * from apuntes";
	$resultado=mysqli_query($conexion,$consulta);
		
	if ($registro=mysqli_fetch_array($resultado)){	
		$json['email'][]=$registro[1];
		$json['titulo'][]=$registro[2];
		$json['desc'][]=$registro[3];
		$json['url'][]=$registro[4];
		$json['degree'][]=$registro[5];
		$json['asignatura'][]=$registro[6];
		while($registro=mysqli_fetch_array($resultado)){
			$json['email'][]=$registro[1];
			$json['titulo'][]=$registro[2];
			$json['desc'][]=$registro[3];
			$json['url'][]=$registro[4];
			$json['degree'][]=$registro[5];
			$json['asignatura'][]=$registro[6];
		}
		echo json_encode($json);
	} else{
		echo "No hay apuntes subidos";
	}
	mysqli_close($conexion);
	
?>
