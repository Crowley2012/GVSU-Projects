<?php
	ini_set('display_errors', 1);
	ini_set('display_startup_errors', 1);
	error_reporting(E_ALL);

	session_start();
	$name=$_SESSION['name'];
	$su=$_SESSION['su'];

	if($name == null || $su == null){
		header("location: assignment13login.php");
	}
?>
<html>
<head>
    <title>Assignment 13</title>
	<script src="assignment13js.js"></script>

	<script type="text/javascript">
		window.onload = function () {
		    addClick();
		}
	</script>
</head>
<body>

	<h1>Assignment 13</h1>

	<fieldset style="display: inline-block;">
		<p style="color:red;" id="error"></p>

		<?php
			$su=$_SESSION['su'];

			if($su == 1){
				echo '<form action="assignment13.php" name="form1" action="form-handler" onsubmit="return validateForm();" method="post">';
			}else{
				echo '<form action="assignment13.php" name="form1" action="form-handler" onsubmit="return validateForm();" method="post" style="display:none">';
			}
		?>
            <code>User</code>
            <input type="text" name="userName" style="margin:2px"/><br/>

            <code>First Name</code>
            <input type="text" name="firstName" style="margin:2px"/><br/>

            <code>Last Name</code>
            <input type="text" name="lastName" style="margin:2px"/><br/>

            <code>Phone Number</code>
            <input id="phone" type="text" name="phoneNumber" style="margin:2px"/><br/>

            <code>Age</code>
            <input type="number" name="age" style="margin:2px"/><br/><br/>

            <input type="submit" name="buttonSubmit" value="Submit"/>
        </form>
        <form action="assignment13.php" name="form2" method="post">
        	<input type="submit" name="buttonLogout" value="Logout"/>
		</form>
    </fieldset>


    <?php
		include 'assignment13function.php';

		if(isset($_POST['buttonLogout'])){
			session_destroy();
			header("Location: assignment13login.php");
		}

		$output = fopen("output.txt", "a") or die("Unable to open file!");

		$value = "";
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
		fclose($output);

		addData();

		getAll($_SESSION['name']);
	?>
</body>
</html>
