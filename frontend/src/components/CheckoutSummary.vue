<script setup>
defineProps({ checkout: { type: Object, required: true } })
const money = (value) => `$${Number(value).toFixed(2)}`
</script>

<template>
  <section class="panel">
    <h2>Order summary</h2>
    <div class="summary-row"><span>Subtotal</span><span>{{ money(checkout.subtotal) }}</span></div>
    <div class="summary-row discount"><span>Returning-customer discount</span><span>−{{ money(checkout.discountAmount) }}</span></div>
    <div class="summary-row total"><span>Total</span><strong>{{ money(checkout.total) }}</strong></div>
  </section>
</template>
