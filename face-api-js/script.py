from fastapi import FastAPI, File, HTTPException
from fastapi.responses import FileResponse
from fastapi.middleware.cors import CORSMiddleware
import uvicorn
import os

app = FastAPI()

# Configure CORS
origins = [
    "http://localhost:8000",
    "http://localhost:8024",  # Add any other origins you need
]
app.add_middleware(
    CORSMiddleware,
    allow_origins=origins,
    allow_credentials=True,
    allow_methods=["GET"],
    allow_headers=["*"],
)

@app.get("/files/{file_path:path}")
async def read_file(file_path: str):
    # Ensure that the requested file is within a specific directory
    base_dir = "/Users/sandeep383.kumar/Desktop/keycloak-custom-authenticator/keycloakFaceAuthPlugin/ss/thomasdarimont keycloak-extension-playground master auth-faceauth-extension-src_main_resources_theme_faceauth-theme_login_resources_js"
    requested_file = os.path.join(base_dir, file_path)
    if not requested_file.startswith(base_dir) or not os.path.exists(requested_file):
        raise HTTPException(status_code=404, detail="File not found")

    # Serve the file using FileResponse
    return FileResponse(requested_file)

if __name__ == "__main__":
    uvicorn.run(app, host="localhost", port=2000)
