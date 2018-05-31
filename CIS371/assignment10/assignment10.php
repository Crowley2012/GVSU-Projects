<?php error_reporting(E_ALL); ?>

<html>
<head>
    <title>Assignment 10</title>
    <style type="text/css">
        #get, #post {
            vertical-align: top;
        }
    </style>
</head>
<body>

	<h1>Assignment 10</h1>

	<table>
		<tr>
		    <td id="post">
		        <fieldset>
		            <legend>Friend Information</legend>
		            <form action="assignment10.php" method="post">
		                <code>First Name</code>
		                <input type="text" name="firstName"/><br/>

		                <code>Last Name</code>
		                <input type="text" name="lastName"/><br/>

		                <code>Phone Number</code>
		                <input type="text" name="phoneNumber"/><br/>

		                <code>Age</code>
		                <input type="number" name="age"/><br/>

		                <input type="submit" name="buttonSubmit" value="Submit"/>
		            </form>
		        </fieldset>

		        <table>
		            <tr>
		                <th colspan=2>Submitted Info</th>
		            </tr>
		            <tr>
		                <th>Field</th>
		                <th>Value</th>
		            </tr>
		            <?php
						$output = fopen("output.txt", "a") or die("Unable to open file!");

				        foreach ($_POST as $key => $value) {
							if($value != "" && $value != "Submit"){
								fwrite($output, $value);
								if($key != "age"){
									fwrite($output, ",");
								}

				            echo "<tr><td>$key</td><td>$value</td></tr>\n";
							}
				        }
						if($value != ""){
							fwrite($output, "\n");
						}
						fclose($myfile);
		            ?>
		        </table>
		    </td>
	</table>
	<?php 
		include 'assignment10functions.php';
		createTable();
		addData();
		getAll();

	?>
</body>
</html>
