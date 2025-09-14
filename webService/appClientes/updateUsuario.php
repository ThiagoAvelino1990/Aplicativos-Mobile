<?php
if ($_SERVER['REQUEST_METHOD'] == 'PUT') {
	
    // Captura e converte os dados x-www-form-urlencoded
    parse_str(file_get_contents("php://input"), $_PUT);
	
    $token 						= $_PUT['token'];
    $usuarioID 					= $_PUT['ID'];
	$nome 						= $_PUT['NOME'];
	$cpfcnpj 					= $_PUT['CPF_CNPJ'];
	$logradouro 				= $_PUT['LOGRADOURO'];
	$complemento 				= $_PUT['COMPLEMENTO'];
	$email 						= $_PUT['EMAIL'];
	$senha 						= $_PUT['SENHA'];	
	$telefone 					= $_PUT['TELEFONE'];
	$lembrar_senha 				= $_PUT['LEMBRAR_SENHA'];
	$atualizar_senha 			= $_PUT['ATUALIZAR_SENHA'];
	$data_de_inclusao 			= $_PUT['DATA_DE_INCLUSAO'];
	$data_de_alteracao 			= $_PUT['DATA_DE_ALTERACAO'];  
    
    if ($token == "xpto" && !is_null($usuarioID)) {
        include_once "dbConnection.php";
        $sql = "UPDATE USUARIO SET ID_TIPO_PESSOA = ?
								  ,NOME = ?
								  ,CPF_CNPJ = ?
								  ,LOGRADOURO = ? 
								  ,COMPLEMENTO = ?
								  ,EMAIL = ?
								  ,SENHA = ?
								  ,TELEFONE =?
								  ,LEMBRAR_SENHA = ?
								  ,ATUALIZAR_SENHA
								  ,DATA_DE_INCLUSAO = ?
								  ,DATA_DE_ALTERACAO = ?
								  
				 WHERE id = ?";
        $statement = $pdo->prepare($sql);

		$statement->bindParam(1, $id_tipo_pessoa);
		$statement->bindParam(2, $nome);
		$statement->bindParam(3, $cpfcnpj);
		$statement->bindParam(4, $logradouro);
		$statement->bindParam(5, $complemento);
		$statement->bindParam(6, $email);
		$statement->bindParam(7, $senha);		
		$statement->bindParam(8, $telefone);		
		$statement->bindParam(9, $lembrar_senha);
		$statement->bindParam(10, $atualizar_senha);
		$statement->bindParam(11, $data_de_inclusao);
		$statement->bindParam(12, $data_de_alteracao);
		$statement->bindParam(13, $usuarioID;		
		

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