// read from files with js.
const fs = require('fs');
const util = require('util');

// Convert fs.readFile into Promise version of same    
const readFile = util.promisify(fs.readFile);

async function getFileData(filePath) {
  return await readFile(filePath, 'utf8');
}





/* call function to read */
/* --------------------- */

// Can't use `await` outside of an async function so you need to chain
// with then()
let myP = 'hi.txt';
getFileData(myP).then(data => {
  console.log(data);
});

