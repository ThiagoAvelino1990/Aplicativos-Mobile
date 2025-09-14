<?php
if ($_SERVER['REQUEST_METHOD'] == 'PUT') {

    // Captura e converte os dados x-www-form-urlencoded
    parse_str(file_get_contents("php://input"), $_PUT);	
    
    $token 				= $_PUT['token'];
    $clienteID 			= $_PUT['ID'];
    $nome 				= $_PUT['NOME'];
	$telefone 			= $_PUT['TELEFONE'];
	$email 				= $_PUT['EMAIL'];
	$cep 				= $_PUT['CEP'];
	$logradouro 		= $_PUT['LOGRADOURO'];
	$complemento 		= $_PUT['COMPLEMENTO'];
	$numero 			= $_PUT['NUMERO'];
	$bairo 				= $_PUT['BAIRRO'];
	$cidade 			= $_PUT['CIDADE'];
	$estado 			= $_PUT['ESTADO'];
	$pais 				= $_PUT['PAIS'];
	$documento 			= $_PUT['DOCUMENTO'];
	$id_tipo_documento 	= $_PUT['ID_TIPO_DOCUMENTO'];
	$id_tipo_pessoa 	= $_PUT['ID_TIPO_PESSOA'];
	$termos_de_uso 		= $_PUT['TERMOS_DE_USO'];
	$data_inclusoa 		= $_PUT['DATA_INCLUSAO'];
	$data_alteracao 	= $_PUT['DATA_ALTERACAO'];
	$id_usuario 		= $_PUT['ID_USUARIO'];    
    
    if ($token == "xpto" && !is_null($clienteID)) {
        include_once "dbConnection.php";
        $sql = "UPDATE CLIENTE SET NOME = ?
								  ,TELEFONE = ?
								  ,EMAIL = ?
								  ,CEP = ? 
								  ,LOGRADOURO = ?
								  ,COMPLEMENTO = ?
								  ,NUMERO = ?
								  ,BAIRRO = ?
								  ,CIDADE = ?
								  ,ESTADO = ?
								  ,PAIS = ?
								  ,DOCUMENTO = ?
								  ,ID_TIPO_DOCUMENTO = ?
								  ,ID_TIPO_PESSOA = ?
								  ,TERMOS_DE_USO = ?
								  ,DATa_INCLUSAO = ?
								  ,DATa_ALTERACAO =?
								  ,ID_USUARIO = ?
								  
				 WHERE ID = ?";
        $statement = $pdo->prepare($sql);
		
		$statement->bindParam(1, $nome);
		$statement->bindParam(2, $telefone);
		$statement->bindParam(3, $email);
		$statement->bindParam(4, $cep);
		$statement->bindParam(5, $logradouro);
		$statement->bindParam(6, $complemento);
		$statement->bindParam(7, $numero);
		$statement->bindParam(8, $bairo);
		$statement->bindParam(9, $cidade);
		$statement->bindParam(10, $estado);
		$statement->bindParam(11, $pais);
		$statement->bindParam(12, $documento);
		$statement->bindParam(13, $id_tipo_documento);
		$statement->bindParam(14, $id_tipo_pessoa);
		$statement->bindParam(15, $termos_de_uso);
		$statement->bindParam(16, $data_inclusoa);
		$statement->bindParam(17, $data_alteracao);
		$statement->bindParam(18, $id_usuario);
		$statement->bindParam(19, $clienteID;		
		

        if($statement->execute()){
			echo json_encode(["Status"=>"Sucesso","Mensagem"=>"UPDATE realizado com sucesso"]);
		}else{
			$error = $statement->errorInfo();			
			echo json_encode(["Status"=>"Erro", "Mensagem"=>"Erro ao realizar o UPDATE ".$error[2]]);			
		}         
       // $results = $statement->fetchAll(PDO::FETCH_ASSOC);
        //echo "<pre>".print_r($results)."</pre"; 
        //echo$json = json_encode($results);
        
    
    }else{
        echo json_encode(["Status"=>"Erro", "Mensagem"=>"Não autorizado"]);
    }
} else {
    echo json_encode(["Status"=>"Erro", "Mensagem"=>"Acesso Negado"]);
}
?>