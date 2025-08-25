<?php
if ($_SERVER['REQUEST_METHOD'] == 'PUT') {
    
    $token = $_GET['token'];
    $usuarioID = $_GET['usuarioID'];
	$nome = $_GET['nome'];
	$cpfcnpj = $_GET['cpfcnpj'];
	$logradouro = $_GET['logradouro'];
	$complemento = $_GET['complemento'];
	$email = $_GET['email'];
	$senha = $_GET['senha'];	
	$telefone = $_GET['telefone'];
	$lembrar_senha = $_GET['lembrar_senha'];
	$atualizar_senha = $_GET['atualizar_senha'];
	$data_de_inclusao = $_GET['data_de_inclusao'];
	$data_de_alteracao = $_GET['data_de_alteracao'];  
    
    if ($token == "xpto" && !is_null($usuarioID)) {
        include_once "dbConnection.php";
        $sql = "UPDATE usuario SET id_tipo_pessoa = ?
								  ,nome = ?
								  ,cpf_cnpj = ?
								  ,logradouro = ? 
								  ,complemento = ?
								  ,email = ?
								  ,senha = ?
								  ,telefone =?
								  ,lembrar_senha = ?
								  ,atualizar_senha
								  ,data_de_inclusao = ?
								  ,data_de_alteracao = ?
								  
				 WHERE id = ?";
        $statement = $pdo->prepare($sql);

		$statement->bindParam(1, $usuarioID;
		$statement->bindParam(2, $id_tipo_pessoa);
		$statement->bindParam(3, $nome);
		$statement->bindParam(4, $cpfcnpj);
		$statement->bindParam(5, $logradouro);
		$statement->bindParam(6, $complemento);
		$statement->bindParam(7, $email);
		$statement->bindParam(8, $senha);		
		$statement->bindParam(9, $telefone);		
		$statement->bindParam(10, $lembrar_senha);
		$statement->bindParam(11, $atualizar_senha);
		$statement->bindParam(12, $data_de_inclusao);
		$statement->bindParam(13, $data_de_alteracao);
		

        $statement->execute();
       // $results = $statement->fetchAll(PDO::FETCH_ASSOC);
        //echo "<pre>".print_r($results)."</pre"; 
        //echo$json = json_encode($results);
        echo "Registro alterado com sucesso";
    
    }else{
        echo "Não autorizado.";
    }
} else {
    echo "Acesso Negado.";
}
?>