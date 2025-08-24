<?php
if($_SERVER['REQUEST_METHOD'] == 'GET'){

    $token = $_GET['token'] ?? null;
    $userID = $_GET['userID'] ?? null;

    if($token == "xpto" && !is_null($userID)){
        include_once "dbConnection.php";
		//==================
		// Buscar Dados Usuarios
		//=================
        $sqlUsuario = "SELECT * FROM usuario WHERE id = ?";
        $statementUsuario = $pdo->prepare($sqlUsuario);
        $statementUsuario->bindParam(1,$userID);
        $statementUsuario->execute();
        $resultsUsuario = $statementUsuario->fetchAll(PDO::FETCH_ASSOC);		
		
		//==================
		// Buscar Dados Clientes
		//=================
        $sqlCliente = "SELECT * FROM cliente WHERE id_usuario = ?";
        $statementCliente = $pdo->prepare($sqlCliente);
        $statementCliente->bindParam(1,$userID);
        $statementCliente->execute();
        $resultsCliente = $statementCliente->fetchAll(PDO::FETCH_ASSOC);
		
        //echo "<pre>".print_r($results)."</pre"; 
		
        $jsonRetorno = array(
		"usuario" => $resultsUsuario,
		"cliente" => $resultsCliente
		);
		
		echo json_encode($jsonRetorno);
        //echo $jsonRetorno;
    }else{
        echo "Não Autorizado";
    }

}else{
    echo "Opção inválida";
}

?>