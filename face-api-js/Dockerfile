# Use the Nginx Alpine image as the base
FROM nginx:alpine

# Set the working directory to Nginx's default directory for serving static files
WORKDIR /usr/share/nginx/html

# Remove any default files in the working directory
RUN rm -rf ./*

# Copy your static files into the container
COPY ./* ./

WORKDIR /etc/nginx

COPY ./nginx.conf ./conf.d/default.conf
# Expose port 3000 for the application
EXPOSE 80
# Start Nginx in the foreground
CMD ["nginx", "-g", "daemon off;"]