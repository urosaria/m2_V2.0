# Use Node base image
FROM node:20

# Set working directory
WORKDIR /app

# Copy package.json and install deps first (cache friendly)
COPY package*.json ./
RUN npm install

# Copy the rest of the app
COPY . .

# Start the app
CMD ["npm", "start"]