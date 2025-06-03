// Theme
    const themeToggle = document.getElementById('theme-toggle');
    themeToggle.onclick = () => {
      document.body.classList.toggle('dark');
      themeToggle.textContent = document.body.classList.contains('dark') ? '☀️' : '🌙';
    };
    // Sprache
    document.getElementById('lang-switcher').onchange = e => {
      window.location.href = e.target.value === 'ru' ? 'index.html' : 'index.de.html';
    };