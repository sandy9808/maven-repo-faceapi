Followed this mostly: https://github.com/thomasdarimont/keycloak-extension-playground/tree/master/auth-faceauth-extension

Tried to run the keycloak extension playground code with face-api.js file hosted on port 2000. We will have to use keycloak version 19.0.3 for this as it uses old java imports.
To run the code:
1. Install java version 11 from here:

https://www.oracle.com/in/java/technologies/javase/jdk11-archive-downloads.html

2. After installing Java 11, host face-api.js locally by following commands:
```bash
cd face-api-js
source venv/bin/activate
python script.py
```

After this start the keycloak container by this:

```bash
bash reset.sh
```