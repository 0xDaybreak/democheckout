<script setup>
defineProps({ checkout: { type: Object, required: true } })
</script>

<template>
  <section class="panel success-panel">
    <p class="eyebrow">ORDER CONFIRMED</p>
    <h2>Thanks for your order.</h2>
    <p>Order #{{ checkout.orderId }} · {{ checkout.status }}</p>
    <p class="hint">Payment reference: {{ checkout.paymentReference }}</p>
  </section>
</template>
