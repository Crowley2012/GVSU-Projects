<?php
	ini_set('display_errors', 1);
	ini_set('display_startup_errors', 1);
	error_reporting(E_ALL);

	$xmlDoc = new DomDocument();
	$xmlDoc->load("input1.html");
	$root = $xmlDoc->documentElement;
	$span = $xmlDoc->getElementsByTagName('span');
?>

<html>
<head>


</head>

<body>
<h1>Special Words</h1>

<h2>All Caps</h2>
<ul>
	<?php
		foreach ($span as $line) {
			if($line->getAttribute('class') == 'allcaps'){
				echo "<li>", $line->nodeValue, PHP_EOL, "</li>";
			}
		}
	?>
</ul>

<h2>Upcase</h2>
<ul>
	<?php
		foreach ($span as $line) {
			if($line->getAttribute('class') == 'upcase'){
				echo "<li>", $line->nodeValue, PHP_EOL, "</li>";
			}
		}
	?>
</ul>

</body>

</html>
