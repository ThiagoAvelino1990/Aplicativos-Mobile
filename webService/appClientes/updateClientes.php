<?php
if ($_SERVER['REQUEST_METHOD'] == 'PUT') {
    
    $token = $_GET['token'];
    $clienteID = $_GET['clienteID'];
    $nome = $_GET['nome'];
	$telefone = $_GET['telefone'];
	$email = $_GET['email'];
	$cep = $_GET['cep'];
	$logradouro = $_GET['logradouro'];
	$complemento = $_GET['complemento'];
	$numero = $_GET['numero'];
	$bairo = $_GET['bairro'];
	$cidade = $_GET['cidade'];
	$estado = $_GET['estado'];
	$pais = $_GET['pais'];
	$documento = $_GET['documento'];
	$id_tipo_documento = $_GET['id_tipo_documento'];
	$id_tipo_pessoa = $_GET['id_tipo_pessoa'];
	$termos_de_uso = $_GET['termos_de_uso'];
	$data_inclusoa = $_GET['data_inclusao'];
	$data_alteracao = $_GET['data_alteracao'];
	$id_usuario = $_GET['id_usuario'];    
    
    if ($token == "xpto" && !is_null($clienteID)) {
        include_once "dbConnection.php";
        $sql = "UPDATE cliente SET nome = ?
								  ,telefone = ?
								  ,email = ?
								  ,cep = ? 
								  ,logradouro = ?
								  ,complemento = ?
								  ,numero = ?
								  ,bairo = ?
								  ,cidade = ?
								  ,estado = ?
								  ,pais = ?
								  ,documento = ?
								  ,id_tipo_documento = ?
								  ,id_tipo_pessoa = ?
								  ,termos_de_uso = ?
								  ,data_inclusao = ?
								  ,data_alteracao =?
								  ,id_usuario = ?
								  
				 WHERE id = ?";
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