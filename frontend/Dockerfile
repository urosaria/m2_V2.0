# --- Build Stage ---
FROM node:20 as builder

WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build

# --- Production Stage ---
FROM node:20-slim

# Install serve to host the static build
RUN npm install -g serve

WORKDIR /app
COPY --from=builder /app/build ./build

# Use serve to host the static files (lightweight)
EXPOSE 3000
CMD ["serve", "-s", "build", "-l", "3000"]