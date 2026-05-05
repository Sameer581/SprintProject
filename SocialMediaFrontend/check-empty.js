const fs = require('fs');
const path = require('path');

const dir = 'c:\\GitPractice\\SprintProject\\SocialMediaFrontend\\src\\app\\components';
const components = fs.readdirSync(dir);
const emptyComponents = [];

components.forEach(comp => {
  const compDir = path.join(dir, comp);
  if (fs.statSync(compDir).isDirectory()) {
    const files = fs.readdirSync(compDir);
    const tsFile = files.find(f => f.endsWith('.component.ts'));
    const htmlFile = files.find(f => f.endsWith('.component.html'));
    
    let isEmpty = false;
    if (tsFile && htmlFile) {
      const tsPath = path.join(compDir, tsFile);
      const htmlPath = path.join(compDir, htmlFile);
      const tsSize = fs.statSync(tsPath).size;
      const htmlSize = fs.statSync(htmlPath).size;
      
      // Default boilerplate sizes: TS ~ 250 bytes, HTML ~ 20-30 bytes
      if (tsSize < 300 && htmlSize < 50) {
        isEmpty = true;
      }
    }
    if (isEmpty) {
      emptyComponents.push(comp);
    }
  }
});

console.log("Empty Components:\n" + emptyComponents.join('\n'));
