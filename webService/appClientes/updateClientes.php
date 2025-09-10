<?php
if ($_SERVER['REQUEST_METHOD'] == 'PUT') {
    
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

		$statement->bindParam(1, $clienteID;
		$statement->bindParam(2, $nome);
		$statement->bindParam(3, $telefone);
		$statement->bindParam(4, $email);
		$statement->bindParam(5, $cep);
		$statement->bindParam(6, $logradouro);
		$statement->bindParam(7, $complemento);
		$statement->bindParam(8, $numero);
		$statement->bindParam(9, $bairo);
		$statement->bindParam(10, $cidade);
		$statement->bindParam(11, $estado);
		$statement->bindParam(12, $pais);
		$statement->bindParam(13, $documento);
		$statement->bindParam(14, $id_tipo_documento);
		$statement->bindParam(15, $id_tipo_pessoa);
		$statement->bindParam(16, $termos_de_uso);
		$statement->bindParam(17, $data_inclusoa);
		$statement->bindParam(18, $data_alteracao);
		$statement->bindParam(19, $id_usuario);
		

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