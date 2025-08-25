<?php
if ($_SERVER['REQUEST_METHOD'] == 'DELETE') {
    
    $usuarioID = $_GET['usuarioID'];
    
    if ($token == "xpto" && !is_null($usuarioID)) {
        include_once "dbConnection.php";
		
        $sql = "DELETE usuario   
				 WHERE id = ?";
        $statement = $pdo->prepare($sql);

		$statement->bindParam(1, $usuarioID;		

        $statement->execute();
       // $results = $statement->fetchAll(PDO::FETCH_ASSOC);
        //echo "<pre>".print_r($results)."</pre"; 
        //echo$json = json_encode($results);
        echo "Registro excluído com sucesso";
    
    }else{
        echo "Não autorizado.";
    }
} else {
    echo "Acesso Negado.";
}
?>