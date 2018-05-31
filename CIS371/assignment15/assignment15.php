<?php
	ini_set('display_errors', 1);
	ini_set('display_startup_errors', 1);
	error_reporting(E_ALL);

	include 'assignment15functions.php';
	include 'assignment15sql.php';

	if(isset($_POST['func']) && !empty($_POST['func'])){
		switch($_POST['func']){
		    case 'getEvents':
		        getEvents($_POST['date']);
		        break;
		    default:
		        break;
		}
	}

	$color = "white";
	$name = "Sean Crowley";

	if(isset($_COOKIE['color'])){
    	$color = $_COOKIE['color'];
	}

	if(isset($_COOKIE['name'])){
    	$name = $_COOKIE['name'];
	}

	if(isset($_GET['color'])){
    	$color = $_GET['color'];
    	setcookie('color', $color, time() + 10);
	}

	if(isset($_GET['name'])){
    	$name = $_GET['name'];
    	setcookie('name', $name, time() + 10);
	}
?>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-type" content="text/html;charset=iso-8859-1"/>
	<link rel="stylesheet" href="assignment15.css" type="text/css"/>
	<title>Assignment 15</title>

	<script src="assignment15.js"></script>
	<script type="text/javascript">
		window.onload = function () {
		    addClick();
		}
	</script>
</head>
<body>
	<center>
	<?php
		echo "<h1>$name</h1>";
	?>

	<h2>Assignment 15</h2>
	<div id="demo"><h2>Event Data</h2></div>
	</center>

	<form method="post">
		<input class="prev" height="10%" width="10%" type="image" src="arrow.png" name="previous" value="Previous"/>

		<?php 
			buildCal();
			//createTableEvents();
			rebuildEvents();
			buildEvent(1);
		?>

		<input class="next" height="10%" width="10%" type="image" src="arrow.png" name="next" value="Next"/>
	</form>
	<?php 
		printEvents();
	?>
</body>
</html>
