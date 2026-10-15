<script setup>
defineProps({ checkout: { type: Object, required: true } })
const money = (value) => `$${Number(value).toFixed(2)}`
</script>

<template>
  <section class="panel success-panel">
    <p class="eyebrow">ORDER CONFIRMED</p>
    <h2>Thanks for your order.</h2>
    <p>Order #{{ checkout.orderId }} · {{ checkout.status }}</p>
    <p v-if="Number(checkout.discountAmount) > 0" class="discount-note">
      You saved {{ money(checkout.discountAmount) }} with your returning-customer discount.
    </p>
    <p class="hint">Payment reference: {{ checkout.paymentReference }}</p>
  </section>
</template>
