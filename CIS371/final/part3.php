<?php
	ini_set('display_errors', 1);
	ini_set('display_startup_errors', 1);
	error_reporting(E_ALL);

	$num = 3;

	if(isset($_GET['num'])){
    	$num = $_GET['num'];
	}
?>
<html>
<head>
    <title>Part 3</title>
	<script src="assignment13js.js"></script>
</head>
<body>

	<h1>Part 3</h1>

	<fieldset style="display: inline-block;">
		<form action="part3.php" name="form1" action="form-handler" onsubmit="return validateForm();" method="post">
			<?php
				for($i=0; $i<$num; $i++){
            		echo '<input type="number" name="age', $i ,'" style="margin:2px"/><br/><br/>';
				}
			?>
            <input type="submit" name="buttonSubmit" value="Submit"/>
        </form>
    </fieldset>


    <?php
		$avg = 0;

        foreach ($_POST as $key => $value) {
			if($value != "" && $value != "Submit"){
				if(!isset($max) || $value > $max){
					$max = $value;
				}
				if(!isset($min) || $value < $min){
					$min = $value;
				}
				$avg += $value;
			}
        }

		$avg = $avg / $num;

		if(isset($max) && isset($min)){
			echo "<br/><br/>Max : ", $max;
			echo "<br/>Min : ", $min;
			echo "<br/>Avg : ", $avg;
		}
	?>
</body>
</html>
