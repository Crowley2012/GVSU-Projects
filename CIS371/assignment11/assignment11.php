<?php
	session_start();
	$name=$_SESSION['name'];
	$su=$_SESSION['su'];

	if($name == null || $su == null){
		header("location: assignment11login.php");
	}
?>
<html>
<head>
    <title>Assignment 11</title>
    <style type="text/css">
        #get, #post {
            vertical-align: top;
        }
    </style>
</head>
<body>

	<h1>Assignment 11</h1>

	<table>
		<tr>
		    <td id="post">
				<?php
					session_start();
					$su=$_SESSION['su'];

					if($su == 1){
						echo '<fieldset>';
					}else{
						echo '<fieldset style="display:none">';
					}
				?>
		            <legend>Friend Information</legend>
		            <form action="assignment11.php" method="post">
		                <code>User</code>
		                <input type="text" name="userName"/><br/>

		                <code>First Name</code>
		                <input type="text" name="firstName"/><br/>

		                <code>Last Name</code>
		                <input type="text" name="lastName"/><br/>

		                <code>Phone Number</code>
		                <input type="text" name="phoneNumber"/><br/>

		                <code>Age</code>
		                <input type="number" name="age"/><br/>

		                <input type="submit" name="buttonSubmit" value="Submit"/>

		                <input type="submit" name="buttonLogout" value="Logout"/>
		            </form>
		        </fieldset>

				<?php
					session_start();
					$su=$_SESSION['su'];

					if($su == 0){
		            	echo '<form action="assignment11.php" method="post">';
		                echo '<input type="submit" name="buttonLogout" value="Logout"/>';
				        echo '</form>';
					}
				?>

		        <table>
		            <?php
						include 'assignment11function.php';

				        $logout=$_POST['buttonLogout'];
						if($logout != null){
							session_destroy();
							header("Location: assignment11login.php");
						}

						$output = fopen("output.txt", "a") or die("Unable to open file!");

				        foreach ($_POST as $key => $value) {
							if($value != "" && $value != "Submit"){
								fwrite($output, $value);
								if($key != "age"){
									fwrite($output, ",");
								}
							}
				        }
						if($value != ""){
							fwrite($output, "\n");
						}
						fclose($myfile);
		
						addData();
						session_start();
						getAll($_SESSION['name']);

		            ?>
		        </table>
		    </td>
	</table>

	<?php
		include 'assignment11function.php';
		session_start();
		$name=$_SESSION['name'];
		$su=$_SESSION['su'];

		echo "<br/>Name : ", $name;

		if($su == 1){
			echo "<br/>Super User : Yes";
		}else{
			echo "<br/>Super User : No";
		}


		//createTableUsers();
		//addDummyUsers();
		//addData();
		//getAll($_SESSION['name']);

	?>
</body>
</html>
