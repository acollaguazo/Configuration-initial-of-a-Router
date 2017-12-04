<?php

//carga y se conecta a la base de datos
require("config.inc.php");

function GetUserIP() {
	if (isset($_SERVER["HTTP_CLIENT_IP"]))
        {
            return $_SERVER["HTTP_CLIENT_IP"];
        }
        elseif (isset($_SERVER["HTTP_X_FORWARDED_FOR"]))
        {
            return $_SERVER["HTTP_X_FORWARDED_FOR"];
        }
        elseif (isset($_SERVER["HTTP_X_FORWARDED"]))
        {
            return $_SERVER["HTTP_X_FORWARDED"];
        }
        elseif (isset($_SERVER["HTTP_FORWARDED_FOR"]))
        {
            return $_SERVER["HTTP_FORWARDED_FOR"];
        }
        elseif (isset($_SERVER["HTTP_FORWARDED"]))
        {
            return $_SERVER["HTTP_FORWARDED"];
        }
        else
        {
            return $_SERVER["REMOTE_ADDR"];
        }
}

function crearLoopBack($ip_router,$numero_loopback,$ip_loopback,$mascara_loopback){
			$port = 23;
			$timeout = 10;
			$router_ip = $ip_router;
			$username = "adm";
			$password = "adm";
			$connection = fsockopen($router_ip, $port, $errno, $errstr, $timeout);
			if(!$connection){
			 return $ip_router;
			 exit();
			} else {
			 fputs($connection, $username."\r\n");
			 fputs($connection, $password."\r\n");
			 fputs($connection, "config t\r\n");
			 fputs($connection, "interface loopback ".$numero_loopback."\r\n");
			 fputs($connection, "ip address ".$numero_loopback." ".$mascara_loopback." \n");
			 fputs($connection, " ");
			  $j = 0;
			   while ($j < 20) {
			    fgets($connection, 20);
			    $j++;
			   }
			   stream_set_timeout($connection, 2);
			   $timeoutCount = 0;
			   return "Connected\n";
			   }	
}//fin func1
function hacerPing($ip_router,$ip_destino,$ip_origen){
		$resultado="";		
		$port = 23;
		$timeout = 10;
		$username = "adm";
		$password = "adm";
		$connection = fsockopen($ip_router, $port, $errno, $errstr, $timeout);
	if(!$connection){
		 return "Error!";
	} else {
		 fputs($connection, "$username\r\n");
		 fputs($connection, "$password\r\n");
		 fputs($connection, "ping ".$ip_destino." "."source ".$ip_origen."\r\n");
		 fputs($connection, " ");
		 $j = 0;
		 while ($j < 7) {
		  fgets($connection, 128);
		  $j++;
		 }
		 stream_set_timeout($connection, 2);
		 $timeoutCount = 0;
		 $j=0;
		 while ($j < 6) {
		  $resul=fgets($connection, 128);
		  if (ereg('#', $resul) ){ 
		      break;
		  }
		  $resultado=$resultado.$resul."\n";
		  $j++;
		 }
	         return $resultado;
	}
}

function noShutdown($ip_router,$interface){
			$port = 23;
			$timeout = 10;
			$username = "adm";
			$password = "adm";
			$connection = fsockopen($ip_router, $port, $errno, $errstr, $timeout);
			if(!$connection){
			 return $ip_router;
			 exit();
			} else {
		         fputs($connection, $username."\r\n");
			 fputs($connection, $password."\r\n");
			 fputs($connection, "config t\r\n");
			 fputs($connection, "interface ".$interface."\r\n");
			 fputs($connection, "no shutdown \r\n ");
			 fputs($connection, "exit\r\n");
			 $j = 0;
			  while ($j < 16) {
			   fgets($connection, 20);
			   $j++;
			  }
			  stream_set_timeout($connection, 2);
			  $timeoutCount = 0;
			  return "Connected\n";
			  }	

}
function shutdown($ip_router,$interface){
			$port = 23;
			$timeout = 10;
			$username = "adm";
			$password = "adm";
			$connection = fsockopen($ip_router, $port, $errno, $errstr, $timeout);
			if(!$connection){
			 return $ip_router;
			 exit();
			} else {
			 echo "Connected\n";
		         fputs($connection, $username."\r\n");
			 fputs($connection, $password."\r\n");
			 fputs($connection, "config t\r\n");
			 fputs($connection, "interface ".$interface."\r\n");
			 fputs($connection, "shutdown \r\n ");
			 fputs($connection, "exit\r\n");
			 $j = 0;
			  while ($j < 16) {
			   fgets($connection, 20);
			   $j++;
			  }
			  stream_set_timeout($connection, 2);
			  $timeoutCount = 0;
			  return "Connected\n";
			  }	
}
function crearPlantillaBasica($ip_router){
			$port = 23;
			$timeout = 10;
			$username = "adm";
			$password = "adm";
			$connection = fsockopen($ip_router, $port, $errno, $errstr, $timeout);
			if(!$connection){
			 return "Error de Conexion";
			} else {
			 fputs($connection, $username."\r\n");
			 fputs($connection, $password."\r\n");
			 fputs($connection, "config t\r\n");
			 fputs($connection, "username adm privilege 15 secret adm\r\n");
			 fputs($connection, "username monitoreo privilege 5 secret monitoreo\r\n");
			 fputs($connection, "banner motd #ACCESO RESTRINGIDO SOLO A PERSONAL AUTORIZADO#\r\n");
			 fputs($connection, "no ip domain-lookup\r\n");
			 fputs($connection, "ip name-server 200.93.195.254\r\n");
			 fputs($connection, "ip domain-name espol.com\r\n");
			 fputs($connection, "line console 0\r\n");
			 fputs($connection, "login local\r\n");
			 fputs($connection, "exec-timeout 3 3\r\n");
			 fputs($connection, "line vty 0 4\r\n");
			 fputs($connection, "login local\r\n");
			 fputs($connection, "exec-timeout 3 3\r\n");
			 fputs($connection, "exit\r\n");
			 fputs($connection, "end\r\n");
			 fputs($connection, "wr\r\n");
			 $j = 0;
			  while ($j < 16) {
			   fgets($connection, 150);
			   $j++;
			  }
			  stream_set_timeout($connection, 2);
			  $timeoutCount = 0;
			  return "Connected\n";
			}
}
function hacerRespaldo($router){
	
}
function crearRutaEstatica($ip_router,$ip_destino,$mascara,$next_hop){
			$port = 23;
			$timeout = 10;
			$router_ip = $ip_router;
			$username = "adm";
			$password = "adm";
			$connection = fsockopen($router_ip, $port, $errno, $errstr, $timeout);
			if(!$connection){
			 return $ip_router;
			 exit();
			} else {
			 echo "Connected\n";
		         fputs($connection, $username."\r\n");
			 fputs($connection, $password."\r\n");
			 fputs($connection, "config t\r\n");
			 fputs($connection, "ip route ".$ip_destino." ".$mascara." ".$next_hop."\r\n");;
			 $j = 0;
			  while ($j < 16) {
			   fgets($connection, 30);
			   $j++;
			  }
			  stream_set_timeout($connection, 2);
			  $timeoutCount = 0;
			  return "Connected\n";
		        }	
}

function showRun($ip_router){
		$resultado="";		
		$port = 23;
		$timeout = 10;
		$username = "adm";
		$password = "adm";
		$connection = fsockopen($ip_router, $port, $errno, $errstr, $timeout);
		if(!$connection){
		 return "Error";
		} else {
		 fputs($connection, "$username\r\n");
		 fputs($connection, "$password\r\n");
		 fputs($connection, "show running-config\r\n");
		 fputs($connection, " ");
		 $j = 0;
		 while ($j < 16) {
		  fgets($connection, 128);
		  $j++;
		 }
		 stream_set_timeout($connection, 2);
		 $timeoutCount = 0;
		 while (!feof($connection)) {
		   $resul=fgets($connection, 128);
		   $info = stream_get_meta_data($connection);
		   if (ereg('#', $resul) ){ 
		      break;
		   }
		   elseif (ereg('--More--', $resul) ){ 
		      fputs ($connection, " ");
		   }
		   else{
		       $resultado=$resultado.$resul."\n";
		   }
		   if ($info['timed_out']) { 
		       $timeoutCount++; // We want to count, how many times repeating.
		   } 
		   if ($timeoutCount >2){ // If repeating more than 2 times,
		      break;   // the connection terminating..
		   }
		 }
	         return $resultado;		 
		 }
}

function showInterfaces($ip_router){
		$resultado="";		
		$port = 23;
		$timeout = 10;
		$username = "adm";
		$password = "adm";
		$connection = fsockopen($ip_router, $port, $errno, $errstr, $timeout);
		if(!$connection){
		 return "Error";
		} else {
		 fputs($connection, "$username\r\n");
		 fputs($connection, "$password\r\n");
		 fputs($connection, "show ip interface brief\r\n");
		 fputs($connection, " ");
		 $j = 0;
		 while ($j < 8) {
		  $resul=fgets($connection, 50);
		  $j++;
		 }
		 stream_set_timeout($connection, 2);
		 $timeoutCount = 0;
		 while (!feof($connection)) {
		   $resul=fgets($connection, 128);
		   $info = stream_get_meta_data($connection);
		   if (ereg('#', $resul) ){ 
		      break;
		   }
		   else{
		       $resultado=$resultado.$resul."\n";
		   }
		   if ($info['timed_out']) { 
		       $timeoutCount++; // We want to count, how many times repeating.
		   } 
		   if ($timeoutCount >2){ // If repeating more than 2 times,
		      break;   // the connection terminating..
		   }
		 }
	         return $resultado;		 
		}
}

function showRoute($ip_router){
		$resultado="";		
		$port = 23;
		$timeout = 10;
		$username = "adm";
		$password = "adm";
		$connection = fsockopen($ip_router, $port, $errno, $errstr, $timeout);
		if(!$connection){
		 return "Error";
		} else {
		 fputs($connection, "$username\r\n");
		 fputs($connection, "$password\r\n");
		 fputs($connection, "show ip route\r\n");
		 fputs($connection, " ");
		 $j = 0;
		 while ($j < 8) {
		  $resul=fgets($connection, 50);
		  $j++;
		 }
		 stream_set_timeout($connection, 2);
		 $timeoutCount = 0;
		 while (!feof($connection)) {
		   $resul=fgets($connection, 128);
		   $info = stream_get_meta_data($connection);
		   if (ereg('#', $resul) ){ 
		      break;
		   }
		   else{
		       $resultado=$resultado.$resul."\n";
		   }
		   if ($info['timed_out']) { 
		       $timeoutCount++; // We want to count, how many times repeating.
		   } 
		   if ($timeoutCount >2){ // If repeating more than 2 times,
		      break;   // the connection terminating..
		   }
		 }
	         return $resultado;		 
		}
}

function showProcess($ip_router){
		$resultado="";		
		$port = 23;
		$timeout = 10;
		$username = "adm";
		$password = "adm";
		$connection = fsockopen($ip_router, $port, $errno, $errstr, $timeout);
		if(!$connection){
		 return "Error";
		} else {
		 fputs($connection, "$username\r\n");
		 fputs($connection, "$password\r\n");
		 fputs($connection, "show process cpu history\r\n");
		 fputs($connection, " ");
		 $j = 0;
		 while ($j < 8) {
		  $resul=fgets($connection, 50);
		  $j++;
		 }
		 stream_set_timeout($connection, 2);
		 $timeoutCount = 0;
		 while (!feof($connection)) {
		   $resul=fgets($connection, 128);
		   $info = stream_get_meta_data($connection);
		   if (ereg('#', $resul) and !ereg(' # ', $resul)){ 
		      break;
		   }
		   elseif (ereg('--More--', $resul) ){ 
		      fputs ($connection, " ");
		   }
		   else{
		       $resultado=$resultado.$resul."\n";
		   }
		   if ($info['timed_out']) { 
		       $timeoutCount++; // We want to count, how many times repeating.
		   } 
		   if ($timeoutCount >2){ // If repeating more than 2 times,
		      break;   // the connection terminating..
		   }
		 }
	         return $resultado;		 
		}
}

$ip = GetUserIP();

if (!empty($_POST)) {
    	if ($_POST['funcion']==1){
		    //obtenemos los usuarios respecto a la usuario que llega por parametro
	    		$query = " 
				    SELECT 
				        name, 
				        pass
				    FROM users 
				    WHERE 
				        name = :username 
					";
	   		 $query_params = array(
					':username' => $_POST['username']
	    				);
	    
	    
	    	try {
			$stmt   = $db->prepare($query);
			$result = $stmt->execute($query_params);
	    	}
	    	catch (PDOException $ex) {
			//para testear pueden utilizar lo de abajo
	      		  //die("la consulta murio " . $ex->getMessage());
		
	      	  $response["success"] = 0;
		  $response["message"] = "Problema con la base de datos, vuelve a intetarlo";
		  die(json_encode($response));
		
	    	}
	    
	   	  //la variable a continuación nos permitirará determinar 
	   	  //si es o no la información correcta
		  //la inicializamos en "false"
	    	  $validated_info = false;
	    
		  //vamos a buscar a todas las filas
		  $row = $stmt->fetch();
		  if ($row) 
	   	 {
			//encaso que no lo este, solo comparamos como acontinuación
			if (md5($_POST['password']) == $row['pass']) 
				{
		 		   $login_ok = true;
			      }
	    	}
		  if ($login_ok) 
	    		{

			$query = " 
				    INSERT INTO 
					log
					(name,IP,logTime) 
				    VALUES 
					(:username,:ip, NOW())
				";
			$query_params = array(
				':username' => $_POST['username'],
				':ip'=>$ip
	    			);
			  try 
			{
				$stmt   = $db->prepare($query);
				$result = $stmt->execute($query_params);
		    	}
			  catch (PDOException $ex) 
			{
			$response["success"] = 0;
			$response["message"] = "Problema con la base de datos, vuelve a intentarlo";
			die(json_encode($response));
			}      
			$response["success"] = 1;
			$response["message"] = "Login correcto!";
			die(json_encode($response));
	    	} 
	    	else 
	    	{
	    	    $response["success"] = 0;
	    	    $response["message"] = "Login INCORRECTO";
	    	    die(json_encode($response));
	    	}
	} //fin funcion 1
    	elseif ($_POST['funcion']==2){ //loopback
	   	$ip_router=$_POST['router'];
		$numero_loopback=$_POST['numeroLoopback'];
		$ip_loopback=$_POST['ip'];
		$mascara_loopback=$_POST['mascara'];
		$response["success"] = 1;
    	    	$response["message"] = crearLoopBack($ip_router,$numero_loopback,$ip_loopback,$mascara_loopback);
		die(json_encode($response));
	}// fin de funcion 2
    	elseif ($_POST['funcion']==3){//ping
		$ip_router= $_POST['router'];
		$ip_destino=$_POST['ipDestino'];
		$ip_origen=$_POST['ipOrigen'];
		$response["success"] = 1;
		$response["message"] ="Exito";
    	    	$resul = hacerPing($ip_router,$ip_destino,$ip_origen);
                $response["text"]=$resul;
		die(json_encode($response));
	}// fin de funcion 3
	elseif ($_POST['funcion']==4){ //no shutdown
		$ip_router=$_POST['router'];
		$interface=$_POST['interface'];
		$response["success"] = 1;
    	    	$response["message"] = noShutdown($ip_router, $interface); 
    	    	die(json_encode($response));
	}// fin de funcion 4
 	elseif ($_POST['funcion']==5){ // shutdown
		$ip_router=$_POST['router'];
		$interface=$_POST['interface'];
		$response["success"] = 1;
    	    	$response["message"] = shutdown($ip_router, $interface); 
		die(json_encode($response));
	}// fin de funcion 5
	elseif ($_POST['funcion']==6){ //plantilla basica
		$ip_router=$_POST['router'];
		$response["success"] = 1;
    	    	$response["message"] = crearPlantillaBasica($ip_router);
		die(json_encode($response));
	}// fin de funcion 6
	elseif ($_POST['funcion']==7){ //hacer respaldo
		$router=$_POST['router']; 
	}// fin de funcion 7
 	elseif ($_POST['funcion']==8){ //crear ruta estatica
		$ip_router=$_POST['router'];
		$ip_destino=$_POST['ipDestino'];
		$mascara=$_POST['mascara'];	
		$next_hop=$_POST['nextHop'];	
		$response["success"] = 1;
    	    	$response["message"] = crearRutaEstatica($ip_router,$ip_destino,$mascara,$next_hop);
		die(json_encode($response));
	}// fin de funcion 8
	elseif ($_POST['funcion']==9){
		$ip_router=$_POST['router'];
		$response["success"] = 1;
		$response["message"] ="Exito";
    	    	$resul = showRun($ip_router);
                $response["text"]=$resul;	
		die(json_encode($response));
	}// fin de funcion 9
	elseif ($_POST['funcion']==10){
		$ip_router=$_POST['router'];
		$response["success"] = 1;
    	    	$resul = showInterfaces($ip_router);
                $response["text"]=$resul;	
		die(json_encode($response));
	}// fin de funcion 10
 	elseif ($_POST['funcion']==11){
		$ip_router=$_POST['router'];
		$response["success"] = 1;
    	    	$resul = showProcess($ip_router);
                $response["text"]=$resul;	
		die(json_encode($response));
	}// fin de funcion 11
	elseif ($_POST['funcion']==12){
		$ip_router=$_POST['router'];
		$response["success"] = 1;
    	    	$resul = showRoute($ip_router);
                $response["text"]=$resul;	
		die(json_encode($response));
	}// fin de funcion 12
} 
else 
{
	?>
	  <h1>Login</h1> 
	  <form action="index.php" method="post"> 
	      Username:<br /> 
	      <input type="text" name="username" placeholder="username" /> 
	      <br /><br /> 
	      Password:<br /> 
	      <input type="password" name="password" placeholder="password" value="" /> 
	      <br /><br /> 
	      <input type="submit" value="Login" /> 
	  </form> 
	  <a href="register.php">Register</a>
	 <?php
}
