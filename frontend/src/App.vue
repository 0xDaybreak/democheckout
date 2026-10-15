<script setup>
import { ref } from 'vue'
import CheckoutSummary from './components/CheckoutSummary.vue'
import OrderConfirmation from './components/OrderConfirmation.vue'

const customerId = ref(1001)
const loading = ref(false)
const error = ref('')
const checkout = ref(null)

async function submitCheckout() {
  loading.value = true
  error.value = ''
  checkout.value = null
  try {
    const response = await fetch('/api/checkout', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({
        customerId: Number(customerId.value),
        items: [{ sku: 'COFFEE', quantity: 1 }]
      })
    })
    if (!response.ok) throw new Error(await response.text())
    checkout.value = await response.json()
  } catch (e) {
    error.value = e.message || 'Checkout failed. Is the backend running?'
  } finally {
    loading.value = false
  }
}
</script>

<template>
  <main class="shell">
    <p class="eyebrow">CODE REVIEW WORKSHOP</p>
    <h1>Checkout</h1>
    <p class="intro">A tiny end-to-end app for exploring the impact of a discount change.</p>

    <section class="panel">
      <label for="customer">Customer ID</label>
      <input id="customer" v-model.number="customerId" type="number" min="1" />
      <p class="hint">Try 1001 (paid order), 1002 (cancelled order), or 2000 (new customer).</p>
      <div class="cart-row"><span>☕ Coffee × 1</span><strong>$12.00</strong></div>
      <button :disabled="loading" @click="submitCheckout">
        {{ loading ? 'Processing…' : 'Place demo order' }}
      </button>
      <p v-if="error" class="error">{{ error }}</p>
    </section>

    <template v-if="checkout">
      <CheckoutSummary :checkout="checkout" />
      <OrderConfirmation :checkout="checkout" />
    </template>
    <p class="footnote">Demo only — no real payment is taken.</p>
  </main>
</template>
