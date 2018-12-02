/*
* try to read data from console.
* if I succeed, I can write my file to console and then read from console
* to my js code!
*/

console.log("Please input text in command line.");

// When user input data and click enter key.
standard_input.on('readable', () => {
	const chunk = process.stdin.read();
	if(chunk != null) {
		process.stdout.write(`data: ${chunk}`);
	}
});
