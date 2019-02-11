<title>Run autotests</title>
<style>
  body {
    font-family: monospace;
    margin: 0;
    padding: 10px;
    width: 100%;
    background-color: black;
    color: white;
    overflow-x: hidden;
  }
  pre {
    background-color: black;
    color: white;
    margin: 10px 0;
    width: 100%;
    display: block;
    white-space: pre-wrap;
  }
</style>
<?php

$id = ($_GET["id"])?($_GET["id"]):"501";
$hash = ($_GET["hash"])?($_GET["hash"]):"false";
echo 'Automation tests for project #' . $id . '<br>';

function liveExecuteCommand($cmd)
{

    while (@ ob_end_flush()); // end all output buffers if any

    $proc = popen("$cmd 2>&1 ; echo Exit status : $?", 'r');

    $live_output     = "";
    $complete_output = "";

    while (!feof($proc))
    {
        $live_output     = fread($proc, 4096);
        $complete_output = $complete_output . $live_output;
        echo "$live_output";
        @ flush();
    }

    pclose($proc);

    // get exit status
    preg_match('/[0-9]+$/', $complete_output, $matches);

    // return exit status and intended output
    return array (
                    'exit_status'  => intval($matches[0]),
                    'output'       => str_replace("Exit status : " . $matches[0], '', $complete_output)
                 );
}

if ( $hash == "true" ) {
  echo '<pre>';
  $result = liveExecuteCommand('mvn exec:java -Did=' . $id . ' test');
  echo '</pre>';

  if($result['exit_status'] === 0){
     // do something if command execution succeeds
     echo '<div style="color:green;font-weight:bold;">Command execution succeeds</div>';
  } else {
      // do something on failure
     echo '<div style="color:red;font-weight:bold;">Failure</div>';
  }

} else {
  echo '<div style="color:red;font-weight:bold;">Permissions denied</div>';
}