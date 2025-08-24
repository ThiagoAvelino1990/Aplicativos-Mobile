 <?php
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    
    $token = $_GET['token'];
    
    $id_tipo_pessoa = $_GET['id_tipo_pessoa'];
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
    
    if ($token == "xpto" {
        include_once "dbConnection.php";
        $sql = "INSERT INTO usuario (idtipopessoa, nome, cpf_cnpj, logradouro, complemento, email, senha, telefone, lembrar_senha, atualizar_senha, data_de_inclusao, data_de_alteracao)									 
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
		

        $statement->execute();
       // $results = $statement->fetchAll(PDO::FETCH_ASSOC);
        //echo "<pre>".print_r($results)."</pre"; 
        //echo$json = json_encode($results);
        echo "Registro incluso com sucesso";
    
    }else{
        echo "Não autorizado.";
    }
} else {
    echo "Acesso Negado.";
}
?>