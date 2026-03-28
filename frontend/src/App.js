import React, { useState } from "react";
import "./App.css";
import { FaUpload, FaDownload } from "react-icons/fa";

function App() {

  const [files, setFiles] = useState([]);
  const [targetLanguage, setTargetLanguage] = useState("JAVA");
  const [result, setResult] = useState("");
  const [loading, setLoading] = useState(false);

  const handleFileChange = (e) => {
    setFiles(e.target.files);
  };

  const handleUpload = async () => {

    if (files.length === 0) {
      alert("Upload files first!");
      return;
    }

    const formData = new FormData();

    for (let i = 0; i < files.length; i++) {
      formData.append("files", files[i]);
    }

    formData.append("targetLanguage", targetLanguage);

    try {
      setLoading(true);

      const response = await fetch("http://localhost:8080/api/files/upload", {
        method: "POST",
        body: formData
      });

      const data = await response.json();
      setResult(data.generatedCode);

    } catch (error) {
      console.error(error);
      alert("Error connecting backend");
    } finally {
      setLoading(false);
    }
  };

  const downloadFile = () => {

    if (!result) return;

    let extension = "txt";

    if (targetLanguage === "JAVA") extension = "java";
    if (targetLanguage === "PYTHON") extension = "py";
    if (targetLanguage === "JAVASCRIPT") extension = "js";

    const blob = new Blob([result], { type: "text/plain" });
    const url = window.URL.createObjectURL(blob);

    const a = document.createElement("a");
    a.href = url;
    a.download = `converted_code.${extension}`;
    a.click();
  };

  return (
    <div className="app">

      <header className="header">
        <h1>🚀 PolyCode Fusion</h1>
        <p>Convert & Merge Code Across Languages</p>
      </header>

      <div className="main">

        <div className="card">

          <h2>Upload Files</h2>

          <input type="file" multiple onChange={handleFileChange} />

          <h3>Select Target Language</h3>

          <select
            value={targetLanguage}
            onChange={(e) => setTargetLanguage(e.target.value)}
          >
            <option value="JAVA">JAVA</option>
            <option value="PYTHON">PYTHON</option>
            <option value="JAVASCRIPT">JAVASCRIPT</option>
          </select>

          <button onClick={handleUpload}>
            <FaUpload /> {loading ? "Processing..." : "Convert Code"}
          </button>

          <div className="tag">
            Target Language: <b>{targetLanguage}</b>
          </div>

        </div>

        <div className="card result-card">

          <div className="result-header">
            <h2>Output Code</h2>

            <button onClick={downloadFile}>
              <FaDownload /> Download
            </button>
          </div>

          <pre>{result || "Your converted code will appear here..."}</pre>

        </div>

      </div>

    </div>
  );
}

export default App;