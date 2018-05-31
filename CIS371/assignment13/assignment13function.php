<?php
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

function createTableUsers(){
	$sql = "create table if not exists users(name varchar(30), password varchar(12), superuser int(1) DEFAULT 0, PRIMARY KEY (name))";
	$conn = connect();
	$result = $conn->query($sql);
}

function createTableFriends(){
	$sql = "create table if not exists friends(id int NOT NULL AUTO_INCREMENT, user varchar(30), fname varchar(30), lname varchar(30), pnum varchar(12), age int(3), PRIMARY KEY (id))";
	$conn = connect();
	$result = $conn->query($sql);
}

function addData(){
	$file="output.txt";
    $fopen = fopen($file, "r");

	if(filesize($file) > 0){
    	$fread = fread($fopen,filesize($file));
	}else{
		$fread = "";
	}

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
		insert($var[0], $var[1], $var[2], $var[3], $var[4]);
	}

	file_put_contents("output.txt", "");
}

function insert($user, $fname, $lname, $pnum, $age){
	$conn = connect();
	$sql = "insert into friends (user, fname, lname, pnum, age) values ('$user', '$fname', '$lname', '$pnum', $age)";
	$result = $conn->query($sql);
}

function getAll($user){
	$sql = "SELECT * FROM friends WHERE user='$user'";
	$conn = connect();
	$result = $conn->query($sql);

	echo "<table id='sqltable' width='50%'>";
	echo "<tr><th>ID</th><th>Name</th><th>Phone</th><th>Age</th></tr>";

	if ($result->num_rows > 0) {
		while($row = $result->fetch_assoc()) {
			echo "<tr><td>", $row["id"], "</td><td>", $row["fname"], " ", $row["lname"],  "</td><td>", $row["pnum"], "</td><td>", $row["age"], "</td></tr>";	
		}
	} else {
		echo "0 results";
	}

	echo "</table>";

	$conn->close();
}

function login($name, $password){
	//echo "<br/><br/>Data";
	//echo "<br/>NAME : ", $name;
	//echo "<br/>PASS : ", $password;

	$sql="SELECT * FROM users WHERE name='$name' and password='$password'";
	$conn = connect();
	$result = $conn->query($sql);

	if ($result->num_rows > 0) {
		session_start();
		$row = $result->fetch_assoc();

		$_SESSION['name'] = $row["name"];
		$_SESSION['su'] = $row["superuser"];

	    echo "<br/><br/>Name: ", $row["name"], "<br/>Password: ", $row["password"], "<br/>Super User: ", $row["superuser"];
		header("Location: assignment13.php");
	}

	$conn->close();
}

function addDummyUsers(){
	$conn = connect();
	$sql = "insert into users (name, password, superuser) values ('sean', 'pass', 1)";
	$result = $conn->query($sql);
	$sql = "insert into users (name, password, superuser) values ('josh', 'asdf', 0)";
	$result = $conn->query($sql);
	$sql = "insert into users (name, password, superuser) values ('chris', '1234', 0)";
	$result = $conn->query($sql);
}

function addDummyFriends(){
	$conn = connect();
	$sql = "insert into friends (user, fname, lname, pnum, age) values ('josh', 'sean', 'crowley', '8159536030', 22)";
	$result = $conn->query($sql);
	$sql = "insert into friends (user, fname, lname, pnum, age) values ('josh', 'caleb', 'wasson', '6661594568', 23)";
	$result = $conn->query($sql);

	$sql = "insert into friends (user, fname, lname, pnum, age) values ('sean', 'josh', 'benoit', '1234567890', 18)";
	$result = $conn->query($sql);
	$sql = "insert into friends (user, fname, lname, pnum, age) values ('sean', 'nikki', 'cheema', '6547893210', 21)";
	$result = $conn->query($sql);
	$sql = "insert into friends (user, fname, lname, pnum, age) values ('sean', 'bailey', 'wedig', '0329514568', 33)";
	$result = $conn->query($sql);
	$sql = "insert into friends (user, fname, lname, pnum, age) values ('sean', 'caleb', 'wasson', '7541593215', 45)";
	$result = $conn->query($sql);

	$sql = "insert into friends (user, fname, lname, pnum, age) values ('chris', 'joe', 'show', '6540259872', 30)";
	$result = $conn->query($sql);
	$sql = "insert into friends (user, fname, lname, pnum, age) values ('chris', 'bob', 'mart', '1593213578', 12)";
	$result = $conn->query($sql);
	$sql = "insert into friends (user, fname, lname, pnum, age) values ('chris', 'jim', 'smith', '3579514568', 25)";
	$result = $conn->query($sql);
}

?>
