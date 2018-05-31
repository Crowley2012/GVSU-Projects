<?php

function printName($name){
	echo "<p>Sean Crowley</p>";
}

function connect(){
	$servername = "127.0.0.1";
	$username = "crowleys";
	$password = "crowleys";
	$dbname = "crowleys";

	// Create connection
	$conn = new mysqli($servername, $username, $password, $dbname);

	// Check connection
	if ($conn->connect_error) {
		echo "ERROR : Connected unsuccessfully<br /><br />";
		die("Connection failed: " . $conn->connect_error);
	} 

	return $conn;
}

function createTable(){
	$sql = "create table if not exists test_table(id int NOT NULL AUTO_INCREMENT, fname varchar(30), lname varchar(30), pnum varchar(12), age int(3), PRIMARY KEY (id))";
	$conn = connect();
	$result = $conn->query($sql);
}

function addData(){
	$file="output.txt";
    $fopen = fopen($file, r);
    $fread = fread($fopen,filesize($file));

    fclose($fopen);

	$fread = trim($fread, PHP_EOL);
    $split = explode(PHP_EOL, $fread);
	
    $array[] = null;

    foreach ($split as $string)
    {
		if($string != ""){
        	$row = explode(",", $string);
        	array_push($array,$row);
		}
    }
	unset($array[0]);
	foreach ( $array as $var ) {
		insert($var[0], $var[1], $var[2], $var[3]);
	}

	file_put_contents("output.txt", "");
}

function insert($fname, $lname, $pnum, $age){
	$conn = connect();
	$sql = "insert into test_table (fname, lname, pnum, age) values ('$fname', '$lname', '$pnum', $age)";
	$result = $conn->query($sql);
}

function getAll(){
	echo "<br />Friends<br /><br />";
	$sql = "SELECT * FROM test_table";
	$conn = connect();
	$result = $conn->query($sql);

	if ($result->num_rows > 0) {
		// output data of each row
		while($row = $result->fetch_assoc()) {
		    echo "ID: " . $row["id"]. " | Name: " . $row["fname"]. " " . $row["lname"]. " | Phone: " . $row["pnum"]." | Age: " . $row["age"].   "<br>";
		}
	} else {
		echo "0 results";
	}
	$conn->close();
}

function addDummy(){
	$conn = connect();
	$sql = "insert into test_table (fname, lname, pnum, age) values ('sean', 'crowley', '8159536030', 22)";
	$result = $conn->query($sql);
	$sql = "insert into test_table (fname, lname, pnum, age) values ('josh', 'benoit', '5558456547', 18)";
	$result = $conn->query($sql);
	$sql = "insert into test_table (fname, lname, pnum, age) values ('nikki', 'cheema', '6541239874', 20)";
	$result = $conn->query($sql);
	$sql = "insert into test_table (fname, lname, pnum, age) values ('joe', 'show', '6540259872', 30)";
	$result = $conn->query($sql);
	$sql = "insert into test_table (fname, lname, pnum, age) values ('bob', 'mart', '1593213578', 12)";
	$result = $conn->query($sql);
	$sql = "insert into test_table (fname, lname, pnum, age) values ('jim', 'smith', '3579514568', 25)";
	$result = $conn->query($sql);
}

?>
