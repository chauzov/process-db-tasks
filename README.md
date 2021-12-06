To create tasks run:

POST http://localhost:8080/api/v1/task
<p>Content-Type: application/json

<pre>
{"tasks": [
{"execTime": 100},
{"execTime": 200},
{"execTime": 300},
{"execTime": 400},
{"execTime": 250},
{"execTime": 150},
{"execTime": 300}
]}
</pre>

Here `execTime`  is the time of the task execution in milliseconds