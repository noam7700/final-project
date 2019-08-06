const puppeteer = require('puppeteer');
const util = require('util');

const fs = require('fs');
// Convert fs.readFile into Promise version of same. so we can read/write sync
const readFile = util.promisify(fs.readFile);
const writeFile = util.promisify(fs.writeFile);


function sleep(ms) {
  return new Promise(resolve => setTimeout(resolve, ms));
}

async function run() {
	//divProduct_7296073231578
	var stdin = process.openStdin();
	
	let prodID = process.argv[2];
	let qty = process.argv[3];
	
	console.log('prodID:' + prodID + ', qty:' + qty);
	
	//discountRequest(7296073231578, 2)
	discountRequest(prodID, qty);
	
}
run();



async function CalcAndPrintDisc(htmlString){
	//retrieving discount
			console.log(htmlString);

		//with space, right after it number is shown: <span class='productDiscount'> 2.80 -$</span>
		s = "<span class='productDiscount'> "; 
		let disc = null;
		if(htmlString.includes(s)){
		index = htmlString.indexOf(s) + s.length; // end of first occurance of s (beginning of pd disc)
		offset = htmlString.substring(index).indexOf(' '); // product's disc length
		disc = htmlString.substring(index, index + offset);
		}
		
		console.log("overall discount = " + disc);
		return disc;
}
async function discountRequest(prodID,qty){
	
	//clean file from previous execution
	var outStream = await fs.createWriteStream('discountResult.txt', {'flags': 'w'});
	outStream.write('');
	outStream.end();
		
	//my outFile's stream
	outStream = await fs.createWriteStream('discountResult.txt', {'flags': 'a'});
	
	
    let browser = await puppeteer.launch({headless: false});
    let page = await browser.newPage();
    await page.setRequestInterception(true); //set the request option (triggered with goto)
    page.on('request', req => {
        //create data
        let remarks = '';
        let unitofmeasure = '';
        var request = {
            AjaxCallAction: "AddProductToBasket",
            paramProductID: prodID,
            paramQuantity: qty,
            paramRemarks: remarks,
            paramUM: unitofmeasure
        }
        // convert JSON to x-www-form-urlencoded
        let reqBody = Object.keys(request).map((k) => {
            return encodeURIComponent(k) + '=' + encodeURIComponent(request[k])
        }).join('&');
        var data = {
            'method': 'POST',
            'url': 'https://www.shufersal.co.il/_layouts/Shufersal_Pages/ajax.aspx',
             // the custom headers
            'headers': {
                'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8',
                'X-Requested-With': 'XMLHttpRequest'
            },
            'postData': reqBody
        };
		console.log("Request Headers: " + request.headers);
		console.log("request: " + request);
        req.continue(data);
    });
    // capture intercepted response
    page.on('response', async response => {
        console.log("Resource Type: "  + response.request().resourceType());
		console.log("Response Headers: " + response.headers);
		console.log("Response: " + response);
		res = await response.text();
		disc = await CalcAndPrintDisc(res);
        
		// print discount to .txt (only if not null)
		if(disc != null){
			await outStream.write(disc+'\n');
		}
        console.log("==============");
    });

    const response = await page.goto('https://www.shufersal.co.il/_layouts/Shufersal_Pages/ajax.aspx');
    // only capture default response
    //console.log(response.statusText());
    //console.log(response.responseText);

	await sleep(5 * 1000); //5 seconds
	
    console.log('done!');
	process.exit();
}

