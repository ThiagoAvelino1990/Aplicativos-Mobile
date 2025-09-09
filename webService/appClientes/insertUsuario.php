<?php
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    
    $token 				= $_POST['token'] ?? null;    
    $id_tipo_pessoa 	= $_POST['ID_TIPO_PESSOA'] ?? null;
	$nome 				= $_POST['NOME'] ?? null;
	$cpfcnpj 			= $_POST['CPF_CNPJ'] ?? null;
	$logradouro 		= $_POST['LOGRADOURO'] ?? null;
	$complemento 		= $_POST['COMPLEMENTO'] ?? null;
	$email 				= $_POST['EMAIL'] ?? null;
	$senha 				= $_POST['SENHA'] ?? null;
	$telefone 			= $_POST['TELEFONE'] ?? null;
	$lembrar_senha 		= $_POST['LEMBRAR_SENHA'] ?? 0;
	$atualizar_senha 	= $_POST['ATUALIZAR_SENHA'] ?? 0;
	$data_de_inclusao 	= $_POST['DATA_DE_INCLUSAO'] ?? null;
	$data_de_alteracao 	= $_POST['DATA_DE_ALTERACAO'] ?? null; 
    
    if ($token == "xpto") {
        include_once "dbConnection.php";
		
        $sql = "INSERT INTO USUARIO (ID_TIPO_PESSOA, NOME, CPF_CNPJ, LOGRADOURO, COMPLEMENTO, EMAIL, SENHA, TELEFONE, LEMBRAR_SENHA, ATUALIZAR_SENHA, DATA_DE_INCLUSAO, DATA_DE_ALTERACAO)									 
				VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
				
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
		

        if($statement->execute()){
			echo json_encode(["Status"=>"Sucesso","Mensagem"=>"Registro incluso com sucesso"]);
		}else{
			$error = $statement->errorInfo();			
			echo json_encode(["Status"=>"Erro", "Mensagem"=>"Erro ao realizar o INSERT ".$error[2]]);			
		}      
        
    
    }else{        
		echo json_encode(["Status"=>"Erro", "Mensagem"=>"Não autorizado"]);			
    }
} else {    
	echo json_encode(["Status"=>"Erro", "Mensagem"=>"Acesso Negado"]);			
}
?>