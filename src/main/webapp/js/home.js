
document.addEventListener('DOMContentLoaded', () => {
  const nav = document.getElementById('menuContainer');
  document.getElementById('menuArrowLeft')
    .addEventListener('click', () => nav.scrollBy({ left: -150, behavior: 'smooth' }));
  document.getElementById('menuArrowRight')
    .addEventListener('click', () => nav.scrollBy({ left: 150, behavior: 'smooth' }));
});
