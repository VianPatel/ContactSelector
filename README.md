# Contact Selector

A program to randomly select contacts from a json file given constraints for stratification such as role and location.

Useful for random sampling of individuals in an organization.

## Usage

Upload `contacts.json` into `src/main/resources`

Contacts should be in the following format:

```json
[{
    "name": "person last, person first",
    "job": "role, location",
    "email": "person@example.com",
    "phone": "(555) 555-1234"
}, {
    "name": "person last2, person first2",
    "job": "role, location",
    "email": "person2@example.com",
    "phone": "(555) 555-1235"
}]
```

compile the jar file with `mvn clean install`

run the jar file located in the `target` directory



## Exporting from external sources

<details>
  <summary>Exporting from Google Contacts Directory</summary>

  The following javascript code can be run in the directory tab:

  ```javascript
function sleep(ms) {
 return new Promise((resolve) => {
 setTimeout(resolve, ms);
 });
}

window.exportedContactsStorage = [];
window.scroller = document.querySelectorAll('.ZvpjBb.C8Dkz')[0].parentElement.parentElement.parentElement.parentElement.parentElement;
while (scroller.scrollHeight - scroller.scrollTop > 400) {
 for (let element of document.querySelectorAll('.ZvpjBb.C8Dkz')[0].querySelectorAll('.XXcuqd')) {
 if (element.firstChild.childNodes.length === 1) {
 break;
 }
 let name = element.firstChild.childNodes[1].innerText;
 let job = element.firstChild.childNodes[4].innerText;
 let email = element.firstChild.childNodes[2].innerText;
 let phone = element.firstChild.childNodes[3].innerText;
 window.exportedContactsStorage.push({'name': name, 'job': job, 'email': email, 'phone': phone});
 }
 scroller.scrollTo({
 top: scroller.scrollTop + 400,
 behavior: 'smooth'
 });
 console.log(
 'Completed iteration;',
 scroller.scrollTop.toString() + '/' + scroller.scrollHeight.toString() +
 ' = ' + (scroller.scrollTop / scroller.scrollHeight * 100).toString() + '%'
 );
 await sleep(500/*(Math.random() * 2 + 0.5)*1000*/);
}
  ```

  Credit: https://www.reddit.com/r/GoogleAppsScript/comments/qrtnfc/comment/ikmgh36

  The exported contacts are saved in json inside the `window.exportedContactsStorage` variable. This should be downloaded and saved as `contacts.json` by running the following in console and copying the string output `JSON.stringify(window.exportedContactsStorage);`

</details>



## Adding support for other organizations

App.java and Contact.java contain enums and switches corresponding to specific roles such as Student and Teacher which should be changed for universal support
