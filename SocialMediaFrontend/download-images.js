const fs = require('fs');
const path = require('path');

const dir = path.join(__dirname, 'src', 'assets', 'images');
if (!fs.existsSync(dir)){
    fs.mkdirSync(dir, { recursive: true });
}

const filenames = [
  'profile1.jpg', 'profile2.jpg', 'profile3.jpg', 'profile4.jpg', 'profile5.jpg',
  'profile6.jpg', 'profile7.jpg', 'profile8.jpg', 'profile9.jpg', 'profile10.jpg',
  'profile11.jpg', 'profile12.jpg', 'profile13.jpg', 'profile14.jpg', 'profile15.jpg',
  'profile16.jpg', 'john_doe.jpg', 'jane_smith.jpg', 'sam_jones.jpg', 'emily_wilson.jpg',
  'alex_jenkins.jpg', 'lisa_miller.jpg', 'tom_smith.jpg', 'olivia_davis.jpg', 'michael_brown.jpg',
  'sophie_miller.jpg', 'david_jones.jpg', 'olivia_martinez.jpg', 'james_taylor.jpg', 'emma_anderson.jpg',
  'william_hall.jpg', 'ava_jackson.jpg', 'samuel_white.jpg', 'hannah_thomas.jpg', 'benjamin_wilson.jpg',
  'zoey_harris.jpg', 'logan_carter.jpg', 'grace_martin.jpg', 'ryan_morris.jpg', 'sophia_king.jpg',
  'daniel_adams.jpg', 'lily_wright.jpg', 'ethan_miller.jpg', 'mia_hill.jpg', 'noah_clark.jpg',
  'mia_jenkins.jpg', 'oliver_wood.jpg', 'emma_morris.jpg', 'liam_turner.jpg', 'ava_nelson.jpg',
  'isabella_rogers.jpg', 'aiden_king.jpg', 'olivia_carter.jpg', 'emma_jones.jpg', 'jacob_smith.jpg',
  'william_brown.jpg', 'sophia_anderson.jpg', 'jackson_wilson.jpg', 'olivia_hill.jpg', 'ethan_carter.jpg',
  'ava_martin.jpg', 'logan_adams.jpg'
];

const categories = ['sea,animal', 'earth,animal', 'bird', 'wildlife', 'cat', 'dog', 'fish', 'lion'];

async function downloadImages() {
  console.log(`Starting download of ${filenames.length} animal profile pictures...`);
  
  for (let i = 0; i < filenames.length; i++) {
    const filename = filenames[i];
    const category = categories[i % categories.length];
    
    // loremflickr provides random images based on tags. The lock parameter prevents browser caching
    const url = `https://loremflickr.com/300/300/${category}?lock=${i}`;
    
    try {
      const response = await fetch(url);
      if (!response.ok) throw new Error(`unexpected response ${response.statusText}`);
      
      const arrayBuffer = await response.arrayBuffer();
      const buffer = Buffer.from(arrayBuffer);
      fs.writeFileSync(path.join(dir, filename), buffer);
      
      console.log(`[${i+1}/62] Downloaded: ${filename} (Category: ${category})`);
    } catch (e) {
      console.error(`Failed to download ${filename}:`, e.message);
    }
  }
  console.log('\nAll profile pictures have been downloaded successfully to src/assets/images/ !');
}

downloadImages();
