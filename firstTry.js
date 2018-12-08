//try to compile my pascal code and compare it with given answer.

const puppeteer = require('puppeteer'); //clasic :)
const fs = require('fs');
const util = require('util');
// Convert fs.readFile into Promise version of same    
const readFile = util.promisify(fs.readFile);

async function getFileData(filePath) {
  return await readFile(filePath, 'utf8');
}
function sleep(ms){
    return new Promise(resolve=>{
        setTimeout(resolve,ms)
    })
}

(async function main() {
	try {
		const browser = await puppeteer.launch({headless: false});
		const page = await browser.newPage();
		
		await page.goto('https://text-compare.com/');
		console.log('opened the website');
		await page.waitForSelector('textarea[name=text1]');
		console.log('website finished loading!');
		
		let myOutFile = './hi.txt';
		let myOutStr;
		getFileData(myOutFile).then(data => {
			myOutStr = data;
		});
		
		//print text-area's name
		let textElement = await page.$('[name=text1]');
		let textElementName = await (await textElement.getProperty('name')).jsonValue();
		console.log('texts name is: ', textElementName);
		
		//write to this text-area1 my out-file
		await page.type('textarea[name=text1]', myOutStr, {delay: 10});
		console.log('myOutStr:\n', myOutStr);
		
		//write my string to text-area2
		let strEx = 'hello!\nhello again!\n';
		page.type('textarea[name=text2]', strEx, {delay: 10});
		
		await sleep(1000);
		//click 'compare' button
		let button_compare = await page.$('button[id=compareButton]');
		await button_compare.click();
		
		
	} catch(e) {
		console.log('my error: ', e);
	}
})();