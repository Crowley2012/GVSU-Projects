<?php
function connect(){
	$servername = "127.0.0.1";
	$username = "crowleys";
	$password = "crowleys";
	$dbname = "crowleys";
	$conn = new mysqli($servername, $username, $password, $dbname);

	if ($conn->connect_error) {
		echo "ERROR : Connected unsuccessfully<br /><br />";
		die("Connection failed: " . $conn->connect_error);
	} 

	return $conn;
}

function createTableEvents(){
	$sql = "create table if not exists events(id int(30) NOT NULL AUTO_INCREMENT, title varchar(30), date datetime, startTime varchar(30), stopTime varchar(30), description varchar(30), PRIMARY KEY (id))";
	$conn = connect();
	$result = $conn->query($sql);
}

function rebuildEvents(){
	$sql = "drop table events";
	$conn = connect();
	$result = $conn->query($sql);

	createTableEvents();
	addDummyEvents();
}

function insertEvent($title, $date, $startTime, $stopTime, $description){
	$conn = connect();
	$sql = "insert into events(title, date, startTime, stopTime, description) values ('$title', '$date', '$startTime', '$stopTime', '$description')";
	$result = $conn->query($sql);
}

function addDummyEvents(){
	insertEvent("Game", date('2016-6-15'), "1:00 pm", "5:00 pm", "Baseball");
	insertEvent("Dance", date('2016-6-12'), "3:00 pm", "4:00 pm", "School Dance");
	insertEvent("Car", date('2016-6-09'), "11:20 am", "2:40 pm", "Clean Car");
	insertEvent("Food", date('2015-1-16'), "11:00 am", "1:00 pm", "Eat Food");
	insertEvent("School", date('2016-2-11'), "2:30 pm", "4:45 pm", "Learn");
}

function getEvents(){
	$sql = "SELECT * FROM events";
	$conn = connect();
	$result = $conn->query($sql);

	$array = array();

	if ($result->num_rows > 0) {
		while($row = $result->fetch_assoc()) {
			$array[] = $row;
		}
	}

	return $array;
}

function printEvents(){
	$sql = "SELECT * FROM events";
	$conn = connect();
	$result = $conn->query($sql);

	echo "<center><table id='sqltable' width='50%'>";
	echo "<tr><th>ID</th><th>Title</th><th>Date</th><th>Start Time</th><th>Stop Time</th><th>Description</th></tr>";

	if ($result->num_rows > 0) {
		while($row = $result->fetch_assoc()) {
			echo "<tr><td>", $row["id"], "</td><td>", $row["title"], "</td><td>", date('Y-m-d', strtotime($row["date"])),  "</td><td>", $row["startTime"], "</td><td>", $row["stopTime"], "</td><td>", $row["description"], "</td></tr>";	
		}
	} else {
		echo "0 results";
	}

	echo "</table></center>";

	$conn->close();
}

function buildEvent($id){
	$sql = "SELECT * FROM events WHERE id=$id";
	$conn = connect();
	$result = $conn->query($sql);

	$file = fopen("test.txt","w");
	//echo fwrite($file,"Hello World. Testing!");
	//if ($result->num_rows > 0) {
	//	while($row = $result->fetch_assoc()) {
	//		$data = "<tr><td>", $row["id"], "</td><td>", $row["title"], "</td><td>", date('Y-m-d', strtotime($row["date"])),  "</td><td>", $row["startTime"], "</td><td>", $row["stopTime"], "</td><td>", $row["description"], "</td></tr>";
			//echo fwrite($file, $data);
	//	}
	//}
	//fclose($file);

	echo "<table>
	<tr>
	<th>Firstname</th>
	<th>Lastname</th>
	<th>Age</th>
	<th>Hometown</th>
	<th>Job</th>
	</tr>";
	while($row = mysqli_fetch_array($result)) {
		echo "<tr>";
		echo "<td>" . $row['id'] . "</td>";
		echo "<td>" . $row['title'] . "</td>";
		echo "<td>" . $row['date'] . "</td>";
		echo "<td>" . $row['startTime'] . "</td>";
		echo "<td>" . $row['stopTime'] . "</td>";
		echo "</tr>";
	}
	echo "</table>";
}
?>
