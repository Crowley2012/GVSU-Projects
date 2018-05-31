<?php

    $color = $_COOKIE['color'];
    $name = $_COOKIE['name'];

	if($color == null){
		$color = $_GET['color'];
    	setcookie('color', $color, time() + 20);
	}

	if($name == null){
		$name = $_GET['name'];
    	setcookie('name', $name, time() + 20);
	}

?>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-type" content="text/html;charset=iso-8859-1"/>
	<link rel="stylesheet" href="cal.css" type="text/css"/>
	<title>CS 371 Assignment 7</title>
</head>
<body>

	<?php
		echo "<body bgcolor='$color'>";
		echo "<h1>$name</h1>";
	?>

	<h2>Assignment 7</h2>

	<form method="post">
		<input class="prev" height="10%" width="10%" type="image" src="arrow.png" name="previous" value="Previous"/>

		<?php 
			include 'calfunctions.php';
			setup();
		?>

		<input class="next" height="10%" width="10%" type="image" src="arrow.png" name="next" value="Next"/>
	</form>
</body>
</html>
