# Generative API Client

This project provides a client for interacting with a generative language model API. The client allows users to send text-based requests and receive structured responses with dynamic properties and required fields.

## Features
- Configurable API key and model selection.
- Supports dynamic response schema with custom properties.
- Allows specifying required fields in the response.
- Uses HTTP for API communication.

## Installation
Clone the repository and ensure you have Java 11 or higher installed.

```sh
git clone <repository_url>
cd <project_directory>
```

## Usage

### Initializing the Client
```java
GenerativeClient generativeClient = new GenerativeClient.Builder()
        .setApiKey("YOUR_API_KEY")
        .setModel(GenerativeModel.GEMINI_2_PRO_EXPERIMENTAL)
        .setTemperature(0.7)
        .build();
```

### Sending a Request
```java
Map<String, String> properties = new HashMap<>();
properties.put("solution_for_plant", "string");
properties.put("thoughts", "string");
properties.put("joke", "string");

GenerativeResponse generativeResponse = generativeClient.sendRequest(
        "papatyalarim soluyor ne yapmaliyim?",
        properties,
        List.of("solution_for_plant"));
```

## API Reference
### `sendRequest`
Sends a text request to the generative model and receives a structured response.

#### Parameters
- `inputText` (String): The text input for the request.
- `dynamicProperties` (Map<String, String>): A map of expected response properties with their types.
- `requiredFields` (List<String>): A list of required fields in the response.

#### Returns
- `GenerativeResponse`: A structured response from the model.

## License
This project is licensed under the MIT License.

